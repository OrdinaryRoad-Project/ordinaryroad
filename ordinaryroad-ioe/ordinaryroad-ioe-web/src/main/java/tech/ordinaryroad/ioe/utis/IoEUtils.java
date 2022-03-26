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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.common.data.page.SortOrder;
import tech.ordinaryroad.commons.core.base.dto.BaseDTO;
import tech.ordinaryroad.ioe.api.request.IoEBaseQueryRequest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2022/3/26
 */
public class IoEUtils {

    public static PageLink requestToPageLink(IoEBaseQueryRequest request) {
        final String textSearch = request.getTextSearch();

        final Integer offset = request.getOffset();
        final Integer limit = request.getLimit();
        final int page = offset / limit;

        final SortOrder sortOrder;
        final String orderByAscProperty = ArrayUtil.get(request.getOrderBy(), 0);
        final String orderByDescProperty = ArrayUtil.get(request.getOrderBy(), 0);
        if (StrUtil.isNotBlank(orderByAscProperty)) {
            sortOrder = new SortOrder(orderByAscProperty, SortOrder.Direction.ASC);
        } else if (StrUtil.isNotBlank(orderByDescProperty)) {
            sortOrder = new SortOrder(orderByDescProperty, SortOrder.Direction.DESC);
        } else {
            sortOrder = null;
        }
        return new PageLink(limit, page, textSearch, sortOrder);
    }

    public static <T, DTO extends BaseDTO> PageInfo<DTO> pageDataToPageInfo(PageData<T> pageData, Function<T, DTO> mapper) {
        final List<DTO> dtoList = pageData.getData().stream().map(mapper).collect(Collectors.toList());

        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(dtoList);
        pageInfo.setTotal(pageData.getTotalElements());
        pageInfo.setHasNextPage(pageData.hasNext());
        pageInfo.calcByNavigatePages(pageData.getTotalPages());

        return pageInfo;
    }

}
