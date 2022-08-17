package com.alibaba.csp.sentinel.dashboard.mapper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.SentinelMetric;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 监控数据表 Mapper 接口
 * </p>
 *
 * @author Yu.Xing
 * @since 2022-08-16
 */
@Mapper
public interface SentinelMetricMapper extends BaseMapper<SentinelMetric> {

}
