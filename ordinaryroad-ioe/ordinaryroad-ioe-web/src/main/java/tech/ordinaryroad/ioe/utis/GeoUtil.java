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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NonNull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.GeometryFixer;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.SpatialRelation;
import org.locationtech.spatial4j.shape.jts.JtsGeometry;
import tech.ordinaryroad.ioe.api.LatLon;

import java.util.*;
import java.util.stream.Collectors;

public class GeoUtil {

    private static final SpatialContext distCtx = SpatialContext.GEO;
    private static final JtsSpatialContext jtsCtx;


    static {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.normWrapLongitude = true;
        jtsCtx = factory.newSpatialContext();
    }

    public static synchronized double distance(LatLon x, LatLon y, RangeUnit unit) {
        Point xLL = distCtx.getShapeFactory().pointXY(x.getLongitude(), x.getLatitude());
        Point yLL = distCtx.getShapeFactory().pointXY(y.getLongitude(), y.getLatitude());
        return unit.fromKm(distCtx.getDistCalc().distance(xLL, yLL) * DistanceUtils.DEG_TO_KM);
    }

    public static synchronized boolean contains(@NonNull String polygonInString, @NonNull LatLon latLon) {
        if (polygonInString.isEmpty() || polygonInString.isBlank()) {
            throw new RuntimeException("Polygon string can't be empty or null!");
        }

        JsonArray polygonsJson = normalizePolygonsJson(JsonParser.parseString(polygonInString).getAsJsonArray());
        List<Geometry> polygons = buildPolygonsFromJson(polygonsJson);
        Set<Geometry> holes = extractHolesFrom(polygons);
        polygons.removeIf(holes::contains);

        Geometry globalGeometry = unionToGlobalGeometry(polygons, holes);
        org.locationtech.jts.geom.Point point = jtsCtx.getShapeFactory().getGeometryFactory()
                .createPoint(new Coordinate(latLon.getLatitude(), latLon.getLongitude()));

        return globalGeometry.contains(point);
    }

    private static Geometry unionToGlobalGeometry(List<Geometry> polygons, Set<Geometry> holes) {
        Geometry globalPolygon = polygons.stream().reduce(Geometry::union).orElseThrow(() ->
                new RuntimeException("Error while calculating globalPolygon - the result of all polygons union is null"));
        Optional<Geometry> globalHole = holes.stream().reduce(Geometry::union);
        if (globalHole.isEmpty()) {
            return globalPolygon;
        } else {
            return globalPolygon.difference(globalHole.get());
        }
    }

    private static JsonArray normalizePolygonsJson(JsonArray polygonsJsonArray) {
        JsonArray result = new JsonArray();
        normalizePolygonsJson(polygonsJsonArray, result);
        return result;
    }

    private static void normalizePolygonsJson(JsonArray polygonsJsonArray, JsonArray result) {
        if (containsArrayWithPrimitives(polygonsJsonArray)) {
            result.add(polygonsJsonArray);
        } else {
            for (JsonElement element : polygonsJsonArray) {
                if (containsArrayWithPrimitives(element.getAsJsonArray())) {
                    result.add(element);
                } else {
                    normalizePolygonsJson(element.getAsJsonArray(), result);
                }
            }
        }
    }

    private static Set<Geometry> extractHolesFrom(List<Geometry> polygons) {
        Map<Geometry, List<Geometry>> polygonsHoles = new HashMap<>();

        for (Geometry polygon : polygons) {
            List<Geometry> holes = polygons.stream()
                    .filter(another -> !another.equalsExact(polygon))
                    .filter(another -> {
                        JtsGeometry currentGeo = jtsCtx.getShapeFactory().makeShape(polygon);
                        JtsGeometry anotherGeo = jtsCtx.getShapeFactory().makeShape(another);

                        boolean currentContainsAnother = currentGeo
                                .relate(anotherGeo)
                                .equals(SpatialRelation.CONTAINS);

                        boolean anotherWithinCurrent = anotherGeo
                                .relate(currentGeo)
                                .equals(SpatialRelation.WITHIN);

                        return currentContainsAnother && anotherWithinCurrent;
                    })
                    .collect(Collectors.toList());

            if (!holes.isEmpty()) {
                polygonsHoles.put(polygon, holes);
            }
        }

        return polygonsHoles.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private static List<Geometry> buildPolygonsFromJson(JsonArray polygonsJsonArray) {
        List<Geometry> polygons = new LinkedList<>();

        for (JsonElement polygonJsonArray : polygonsJsonArray) {
            polygons.add(
                    buildPolygonFromCoordinates(parseCoordinates(polygonJsonArray.getAsJsonArray()))
            );
        }

        return polygons;
    }

    private static Geometry buildPolygonFromCoordinates(List<Coordinate> coordinates) {
        if (coordinates.size() == 2) {
            Coordinate a = coordinates.get(0);
            Coordinate c = coordinates.get(1);
            coordinates.clear();

            Coordinate b = new Coordinate(a.x, c.y);
            Coordinate d = new Coordinate(c.x, a.y);
            coordinates.addAll(List.of(a, b, c, d, a));
        }

        CoordinateSequence coordinateSequence = jtsCtx
                .getShapeFactory()
                .getGeometryFactory()
                .getCoordinateSequenceFactory()
                .create(coordinates.toArray(new Coordinate[0]));

        return GeometryFixer.fix(jtsCtx.getShapeFactory().getGeometryFactory().createPolygon(coordinateSequence));
    }

    private static List<Coordinate> parseCoordinates(JsonArray coordinatesJson) {
        List<Coordinate> result = new LinkedList<>();

        for (JsonElement coords : coordinatesJson) {
            double x = coords.getAsJsonArray().get(0).getAsDouble();
            double y = coords.getAsJsonArray().get(1).getAsDouble();
            result.add(new Coordinate(x, y));
        }

        if (result.size() >= 3) {
            result.add(result.get(0));
        }

        return result;
    }

    private static boolean containsPrimitives(JsonArray array) {
        for (JsonElement element : array) {
            return element.isJsonPrimitive();
        }

        return false;
    }

    private static boolean containsArrayWithPrimitives(JsonArray array) {
        for (JsonElement element : array) {
            if (!containsPrimitives(element.getAsJsonArray())) {
                return false;
            }
        }

        return true;
    }

}
