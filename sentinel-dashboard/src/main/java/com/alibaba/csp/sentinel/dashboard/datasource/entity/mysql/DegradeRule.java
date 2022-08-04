package com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yu.xing
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("degrade_rule")
public class DegradeRule implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("app")
    private String app;

    @TableField("ip")
    private String ip;

    @TableField("port")
    private Integer port;

    @TableField("resource")
    private String resource;

    @TableField("limitApp")
    private String limitApp;

    @TableField("count")
    private Double count;

    @TableField("timeWindow")
    private Integer timeWindow;

    @TableField("grade")
    private Integer grade;

    @TableField("minRequestAmount")
    private Integer minRequestAmount;

    @TableField("slowRatioThreshold")
    private Double slowRatioThreshold;

    @TableField("statIntervalMs")
    private Integer statIntervalMs;

    @TableField("gmtCreate")
    private Date gmtCreate;

    @TableField("gmtModified")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String APP = "app";

    public static final String IP = "ip";

    public static final String PORT = "port";

    public static final String RESOURCE = "resource";

    public static final String LIMITAPP = "limitApp";

    public static final String COUNT = "count";

    public static final String TIMEWINDOW = "timeWindow";

    public static final String GRADE = "grade";

    public static final String MINREQUESTAMOUNT = "minRequestAmount";

    public static final String SLOWRATIOTHRESHOLD = "slowRatioThreshold";

    public static final String STATINTERVALMS = "statIntervalMs";

    public static final String GMTCREATE = "gmtCreate";

    public static final String GMTMODIFIED = "gmtModified";

}
