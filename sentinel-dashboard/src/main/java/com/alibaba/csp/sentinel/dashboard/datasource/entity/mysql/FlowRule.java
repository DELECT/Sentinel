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
@TableName("flow_rule")
public class FlowRule implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("app")
    private String app;

    @TableField("ip")
    private String ip;

    @TableField("port")
    private Integer port;

    @TableField("limit_app")
    private String limitApp;

    @TableField("resource")
    private String resource;

    @TableField("grade")
    private Integer grade;

    @TableField("count")
    private Double count;

    @TableField("strategy")
    private Integer strategy;

    @TableField("ref_resource")
    private String refResource;

    @TableField("control_behavior")
    private Integer controlBehavior;

    @TableField("warm_ip_period_sec")
    private Integer warmIpPeriodSec;

    @TableField("max_queueing_time_ms")
    private Integer maxQueueingTimeMs;

    @TableField("cluster_mode")
    private Integer clusterMode;

    @TableField("cluster_config_str")
    private String clusterConfigStr;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String APP = "app";

    public static final String IP = "ip";

    public static final String PORT = "port";

    public static final String LIMIT_APP = "limit_app";

    public static final String RESOURCE = "resource";

    public static final String GRADE = "grade";

    public static final String COUNT = "count";

    public static final String STRATEGY = "strategy";

    public static final String REF_RESOURCE = "ref_resource";

    public static final String CONTROL_BEHAVIOR = "control_behavior";

    public static final String WARM_IP_PERIOD_SEC = "warm_ip_period_sec";

    public static final String MAX_QUEUEING_TIME_MS = "max_queueing_time_ms";

    public static final String CLUSTER_MODE = "cluster_mode";

    public static final String CLUSTER_CONFIG = "cluster_config_str";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
