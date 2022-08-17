package com.alibaba.csp.sentinel.dashboard.service.mysql.impl;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.SentinelMetric;
import com.alibaba.csp.sentinel.dashboard.mapper.SentinelMetricMapper;
import com.alibaba.csp.sentinel.dashboard.service.mysql.SentinelMetricService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 监控数据表 服务实现类
 * </p>
 *
 * @author Yu.Xing
 * @since 2022-08-16
 */
@Service
public class SentinelMetricServiceImpl extends ServiceImpl<SentinelMetricMapper, SentinelMetric> implements SentinelMetricService {

}
