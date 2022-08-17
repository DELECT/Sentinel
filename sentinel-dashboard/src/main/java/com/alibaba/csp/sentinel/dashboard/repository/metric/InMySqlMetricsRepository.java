package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.SentinelMetric;
import com.alibaba.csp.sentinel.dashboard.service.mysql.SentinelMetricService;
import com.alibaba.csp.sentinel.dashboard.util.DateUtils;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component(value = "inMySqlMetricsRepository")
public class InMySqlMetricsRepository implements MetricsRepository<MetricEntity> {

    @Resource
    private SentinelMetricService sentinelMetricService;

    @Override
    public void save(MetricEntity metric) {
        if (metric == null || StringUtil.isBlank(metric.getApp())) {
            return;
        }
        SentinelMetric sentinelMetric = new SentinelMetric();
        BeanUtils.copyProperties(metric, sentinelMetric);
        sentinelMetricService.save(sentinelMetric);
    }

    @Override
    public void saveAll(Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            return;
        }
        metrics.forEach(this::save);

    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        ArrayList<MetricEntity> metricEntitiesResult = new ArrayList<>();

        if (StringUtil.isBlank(app)) {
            return metricEntitiesResult;
        }
        if (StringUtil.isBlank(resource)) {
            return metricEntitiesResult;
        }
        QueryWrapper<SentinelMetric> sentinelMetricQueryWrapper = new QueryWrapper<SentinelMetric>();

        try {
            sentinelMetricQueryWrapper.eq(SentinelMetric.APP, app);
            sentinelMetricQueryWrapper.eq(SentinelMetric.RESOURCE, resource);
            sentinelMetricQueryWrapper.between(SentinelMetric.GMT_CREATE, DateUtils.longToDate(startTime), DateUtils.longToDate(endTime));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<SentinelMetric> list = sentinelMetricService.list(sentinelMetricQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return metricEntitiesResult;
        }
        list.forEach(item -> {
            MetricEntity metricEntity = new MetricEntity();
            BeanUtils.copyProperties(item, metricEntity);
            metricEntitiesResult.add(metricEntity);
        });
        return metricEntitiesResult;
    }

    @Override
    public List<String> listResourcesOfApp(String app) {
        ArrayList<String> result = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return result;
        }
        final long minTimeMs = System.currentTimeMillis() - 1000 * 60 * 5;
        List<SentinelMetric> list;
        try {
            Date date = DateUtils.longToDate(minTimeMs);
            list = sentinelMetricService.list(new QueryWrapper<SentinelMetric>().eq(SentinelMetric.APP, app).gt(SentinelMetric.TIMESTAMP, date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map<String, MetricEntity> resourceCount = new ConcurrentHashMap<>(32);

        List<MetricEntity> metricEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SentinelMetric item : list) {
                MetricEntity metricEntity = new MetricEntity();
                BeanUtils.copyProperties(item, metricEntity);
                metricEntities.add(metricEntity);
            }
        }

        metricEntities.forEach(item -> {
            String resource = item.getResource();
            if (resourceCount.containsKey(resource)) {
                MetricEntity oldEntity = resourceCount.get(item.getResource());
                oldEntity.addPassQps(item.getPassQps());
                oldEntity.addRtAndSuccessQps(item.getRt(), item.getSuccessQps());
                oldEntity.addBlockQps(item.getBlockQps());
                oldEntity.addExceptionQps(item.getExceptionQps());
                oldEntity.addCount(1);
            } else {
                resourceCount.put(resource, MetricEntity.copyOf(item));
            }
        });

        return resourceCount.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    MetricEntity e1 = o1.getValue();
                    MetricEntity e2 = o2.getValue();
                    int t = e2.getBlockQps().compareTo(e1.getBlockQps());
                    if (t != 0) {
                        return t;
                    }
                    return e2.getPassQps().compareTo(e1.getPassQps());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
