/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.ioe.utis;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: 用于点与多边形位置判断
 */
public class GraphUtils {

    /**
     * 判断点是否在多边形内(基本思路是用交点法)
     *
     * @param point
     * @param boundaryPoints
     * @return
     */
    public static boolean isPointInPolygon(BmapPoint point, BmapPoint[] boundaryPoints) {
        // 防止第一个点与最后一个点相同
        if (boundaryPoints != null && boundaryPoints.length > 0
                && boundaryPoints[boundaryPoints.length - 1].equals(boundaryPoints[0])) {
            boundaryPoints = Arrays.copyOf(boundaryPoints, boundaryPoints.length - 1);
        }
        int pointCount = boundaryPoints.length;

        // 首先判断点是否在多边形的外包矩形内，如果在，则进一步判断，否则返回false
        if (!isPointInRectangle(point, boundaryPoints)) {
            return false;
        }

        // 如果点与多边形的其中一个顶点重合，那么直接返回true
        for (int i = 0; i < pointCount; i++) {
            if (point.equals(boundaryPoints[i])) {
                return true;
            }
        }

        /**
         * 基本思想是利用X轴射线法，计算射线与多边形各边的交点，如果是偶数，则点在多边形外，否则在多边形内。还会考虑一些特殊情况，如点在多边形顶点上
         * ， 点在多边形边上等特殊情况。
         */
        // X轴射线与多边形的交点数
        int intersectPointCount = 0;
        // X轴射线与多边形的交点权值
        float intersectPointWeights = 0;
        // 浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        // 边P1P2的两个端点
        BmapPoint point1 = boundaryPoints[0], point2;
        // 循环判断所有的边
        for (int i = 1; i <= pointCount; i++) {
            point2 = boundaryPoints[i % pointCount];

            /**
             * 如果点的y坐标在边P1P2的y坐标开区间范围之外，那么不相交。
             */
            if (point.getLat() < Math.min(point1.getLat(), point2.getLat())
                    || point.getLat() > Math.max(point1.getLat(), point2.getLat())) {
                point1 = point2;
                continue;
            }

            /**
             * 此处判断射线与边相交
             */
            if (point.getLat() > Math.min(point1.getLat(), point2.getLat())
                    && point.getLat() < Math.max(point1.getLat(), point2.getLat())) {// 如果点的y坐标在边P1P2的y坐标开区间内
                if (point1.getLng() == point2.getLng()) {// 若边P1P2是垂直的
                    if (point.getLng() == point1.getLng()) {
                        // 若点在垂直的边P1P2上，则点在多边形内
                        return true;
                    } else if (point.getLng() < point1.getLng()) {
                        // 若点在在垂直的边P1P2左边，则点与该边必然有交点
                        ++intersectPointCount;
                    }
                } else {// 若边P1P2是斜线
                    if (point.getLng() <= Math.min(point1.getLng(), point2.getLng())) {// 点point的x坐标在点P1和P2的左侧
                        ++intersectPointCount;
                    } else if (point.getLng() > Math.min(point1.getLng(), point2.getLng())
                            && point.getLng() < Math.max(point1.getLng(), point2.getLng())) {// 点point的x坐标在点P1和P2的x坐标中间
                        double slopeDiff = 0.0d;
                        if (point1.getLat() > point2.getLat()) {
                            slopeDiff = (point.getLat() - point2.getLat()) / (point.getLng() - point2.getLng())
                                    - (point1.getLat() - point2.getLat()) / (point1.getLng() - point2.getLng());
                        } else {
                            slopeDiff = (point.getLat() - point1.getLat()) / (point.getLng() - point1.getLng())
                                    - (point2.getLat() - point1.getLat()) / (point2.getLng() - point1.getLng());
                        }
                        if (slopeDiff > 0) {
                            if (slopeDiff < precision) {// 由于double精度在计算时会有损失，故匹配一定的容差。经试验，坐标经度可以达到0.0001
                                // 点在斜线P1P2上
                                return true;
                            } else {
                                // 点与斜线P1P2有交点
                                intersectPointCount++;
                            }
                        }
                    }
                }
            } else {
                // 边P1P2水平
                if (point1.getLat() == point2.getLat()) {
                    if (point.getLng() <= Math.max(point1.getLng(), point2.getLng())
                            && point.getLng() >= Math.min(point1.getLng(), point2.getLng())) {
                        // 若点在水平的边P1P2上，则点在多边形内
                        return true;
                    }
                }
                /**
                 * 判断点通过多边形顶点
                 */
                if (((point.getLat() == point1.getLat() && point.getLng() < point1.getLng()))
                        || (point.getLat() == point2.getLat() && point.getLng() < point2.getLng())) {
                    if (point2.getLat() < point1.getLat()) {
                        intersectPointWeights += -0.5;
                    } else if (point2.getLat() > point1.getLat()) {
                        intersectPointWeights += 0.5;
                    }
                }
            }
            point1 = point2;
        }

        if ((intersectPointCount + Math.abs(intersectPointWeights)) % 2 == 0) {// 偶数在多边形外
            return false;
        } else { // 奇数在多边形内
            return true;
        }
    }

    /**
     * 判断点是否在矩形内在矩形边界上，也算在矩形内(根据这些点，构造一个外包矩形)
     *
     * @param point          点对象
     * @param boundaryPoints 矩形边界点
     * @return
     */
    public static boolean isPointInRectangle(BmapPoint point, BmapPoint[] boundaryPoints) {
        BmapPoint southWestPoint = getSouthWestPoint(boundaryPoints); // 西南角点
        BmapPoint northEastPoint = getNorthEastPoint(boundaryPoints); // 东北角点
        return (point.getLng() >= southWestPoint.getLng() && point.getLng() <= northEastPoint.getLng()
                && point.getLat() >= southWestPoint.getLat() && point.getLat() <= northEastPoint.getLat());

    }

    /**
     * 根据这组坐标，画一个矩形，然后得到这个矩形西南角的顶点坐标
     *
     * @param vertexs
     * @return
     */
    private static BmapPoint getSouthWestPoint(BmapPoint[] vertexs) {
        double minLng = vertexs[0].getLng(), minLat = vertexs[0].getLat();
        for (BmapPoint bmapPoint : vertexs) {
            double lng = bmapPoint.getLng();
            double lat = bmapPoint.getLat();
            if (lng < minLng) {
                minLng = lng;
            }
            if (lat < minLat) {
                minLat = lat;
            }
        }
        return new BmapPoint(minLng, minLat);
    }

    /**
     * 根据这组坐标，画一个矩形，然后得到这个矩形东北角的顶点坐标
     *
     * @param vertexs
     * @return
     */
    private static BmapPoint getNorthEastPoint(BmapPoint[] vertexs) {
        double maxLng = 0.0d, maxLat = 0.0d;
        for (BmapPoint bmapPoint : vertexs) {
            double lng = bmapPoint.getLng();
            double lat = bmapPoint.getLat();
            if (lng > maxLng) {
                maxLng = lng;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
        }
        return new BmapPoint(maxLng, maxLat);
    }

    /**
     * @Description: 用于构造地图中的经纬点
     */
    public static class BmapPoint implements Serializable {
        private static final long serialVersionUID = 8114980563368312804L;
        private double lng;// 经度
        private double lat;// 纬度

        public BmapPoint() {
        }

        public BmapPoint(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

}
