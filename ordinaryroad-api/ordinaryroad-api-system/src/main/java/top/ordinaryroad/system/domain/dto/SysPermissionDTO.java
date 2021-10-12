package top.ordinaryroad.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;

import java.time.LocalDateTime;

/**
 * SysPermissionDTO
 *
 * @author mjz
 * @date 2021/10/12
 */
@EqualsAndHashCode(of = {"code"}, callSuper = false)
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel
public class SysPermissionDTO extends BaseDTO {

    private static final long serialVersionUID = 4240891728883871063L;

    @ApiModelProperty("自增主键Id")
    private Long id;

    @ApiModelProperty("主键Uuid")
    private String uuid;

    @ApiModelProperty("权限名")
    private String name;

    @ApiModelProperty("权限code")
    private String code;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
