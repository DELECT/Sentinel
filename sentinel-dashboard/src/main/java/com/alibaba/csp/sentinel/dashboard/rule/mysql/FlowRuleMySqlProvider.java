package com.alibaba.csp.sentinel.dashboard.rule.mysql;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.FlowRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.service.mysql.FlowRuleService;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xingyu
 * @Description
 **/
@Component("flowRuleMySqlProvider")
public class FlowRuleMySqlProvider implements DynamicRuleMySqlProvider<List<FlowRuleEntity>> {

    @Resource
    private FlowRuleService flowRuleService;

    @Override
    public List<FlowRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        if (StringUtil.isBlank(appName)) {
            return new ArrayList<>();
        }
        List<FlowRule> list = flowRuleService.list(new QueryWrapper<FlowRule>()
                .eq(FlowRule.APP, appName)
                .eq(FlowRule.IP, ip)
                .eq(FlowRule.PORT, port));
        return convert(list);
    }

    public List<FlowRuleEntity> convert(List<FlowRule> flowRuleList) {
        ArrayList<FlowRuleEntity> flowRuleEntities = new ArrayList<>();
        if (CollectionUtils.isEmpty(flowRuleList)) {
            return flowRuleEntities;
        }
        flowRuleList.forEach(item -> {
            FlowRuleEntity flowRuleEntity = new FlowRuleEntity();
            BeanUtils.copyProperties(item, flowRuleEntity);
            String clusterConfigStr = item.getClusterConfigStr();
            if (!StringUtil.isBlank(clusterConfigStr)) {
                ClusterFlowConfig clusterFlowConfig = JSONObject.parseObject(clusterConfigStr, ClusterFlowConfig.class);
                flowRuleEntity.setClusterConfig(clusterFlowConfig);
            }
            flowRuleEntities.add(flowRuleEntity);
        });
        return flowRuleEntities;
    }

}
