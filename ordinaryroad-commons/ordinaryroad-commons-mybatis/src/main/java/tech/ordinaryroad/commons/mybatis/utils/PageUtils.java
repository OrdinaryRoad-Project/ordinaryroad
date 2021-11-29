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
