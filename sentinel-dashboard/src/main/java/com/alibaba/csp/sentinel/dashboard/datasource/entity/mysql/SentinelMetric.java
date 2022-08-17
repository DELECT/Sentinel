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
 * 监控数据表
 * </p>
 *
 * @author Yu.Xing
 * @since 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sentinel_metric")
public class SentinelMetric implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;

    @TableField("app")
    private String app;

    @TableField("timestamp")
    private Date timestamp;

    @TableField("resource")
    private String resource;

    @TableField("pass_qps")
    private Long passQps;

    @TableField("success_qps")
    private Long successQps;

    @TableField("block_qps")
    private Long blockQps;

    @TableField("exception_qps")
    private Long exceptionQps;

    @TableField("rt")
    private Double rt;

    @TableField("_count")
    private Long count;

    @TableField("resource_code")
    private Integer resourceCode;


    public static final String ID = "id";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    public static final String APP = "app";

    public static final String TIMESTAMP = "timestamp";

    public static final String RESOURCE = "resource";

    public static final String PASS_QPS = "pass_qps";

    public static final String SUCCESS_QPS = "success_qps";

    public static final String BLOCK_QPS = "block_qps";

    public static final String EXCEPTION_QPS = "exception_qps";

    public static final String RT = "rt";

    public static final String _COUNT = "_count";

    public static final String RESOURCE_CODE = "resource_code";

}
