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

package tech.ordinaryroad.commons.mybatis.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://blog.csdn.net/Ep_Little_prince/article/details/102869479
 */
public class PageUtils {

    /**
     * PageInfo DO转DTO
     *
     * @param doPage DO Page
     * @param <DO>   DO
     * @param <DTO>  DTO
     * @return DTO PageInfo
     */
    public static <DO, DTO> PageInfo<DTO> pageInfoDo2PageListDto(Page<DO> doPage) {
        PageInfo<DO> doPageInfo = PageInfo.of(doPage);

        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<DTO> dtoPage = new Page<>(doPageInfo.getPageNum(), doPageInfo.getPageSize());

        dtoPage.setTotal(doPageInfo.getTotal());
        PageInfo<DTO> dtoPageInfo = new PageInfo<>(dtoPage);
        dtoPageInfo.setStartRow(doPageInfo.getStartRow());
        dtoPageInfo.setEndRow(doPageInfo.getEndRow());
        dtoPageInfo.setSize(doPageInfo.getSize());

        return dtoPageInfo;
    }


    /**
     * PageInfo DO转DTO
     *
     * @param doPage DO Page
     * @param <DO>   DO
     * @param <DTO>  DTO
     * @return DTO PageInfo
     */
    public static <DO, DTO> PageInfo<DTO> pageInfoDo2PageInfoDto(Page<DO> doPage) {
        PageInfo<DO> doPageInfo = PageInfo.of(doPage);

        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<DTO> dtoPage = new Page<>(doPageInfo.getPageNum(), doPageInfo.getPageSize());

        dtoPage.setTotal(doPageInfo.getTotal());
        PageInfo<DTO> dtoPageInfo = new PageInfo<>(dtoPage);
        dtoPageInfo.setStartRow(doPageInfo.getStartRow());
        dtoPageInfo.setEndRow(doPageInfo.getEndRow());
        dtoPageInfo.setSize(doPageInfo.getSize());

        return dtoPageInfo;
    }

    /**
     * PageInfo DO转DTO
     *
     * @param doPage DO Page
     * @param <DO>   DO
     * @param <DTO>  DTO
     * @return DTO PageInfo
     */
    public static <DO, DTO> PageInfo<DTO> pageInfoDo2PageInfoDto(Page<DO> doPage, Function<DO, DTO> mapper) {
        PageInfo<DTO> objectPageInfo = pageInfoDo2PageInfoDto(doPage);
        objectPageInfo.setList(doPage.stream().map(mapper).collect(Collectors.toList()));
        return objectPageInfo;
    }

}
