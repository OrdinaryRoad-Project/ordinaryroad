package top.ordinaryroad.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import top.ordinaryroad.commons.core.annotation.Excel;
import top.ordinaryroad.commons.core.annotation.Excel.ColumnType;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author ruoyi
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "sys_login_info")
public class SysLoginInfoDO extends BaseDO {

    private static final long serialVersionUID = 2510477006396115905L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 状态 0成功 1失败
     */
    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String ipaddr;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;

}