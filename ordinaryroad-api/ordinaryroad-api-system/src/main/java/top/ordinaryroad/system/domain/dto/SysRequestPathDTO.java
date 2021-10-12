package top.ordinaryroad.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * SysPermissionDTO
 *
 * @author mjz
 * @date 2021/10/12
 */
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel
public class SysRequestPathDTO extends BaseDTO {

    private static final long serialVersionUID = -1723903791063911678L;

    @ApiModelProperty("自增主键Id")
    private Long id;

    @ApiModelProperty("主键Uuid")
    private String uuid;

    @ApiModelProperty("请求路径名")
    private String name;

    @ApiModelProperty("请求路径")
    private String url;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("权限")
    private Set<SysPermissionDTO> permissions;

}
