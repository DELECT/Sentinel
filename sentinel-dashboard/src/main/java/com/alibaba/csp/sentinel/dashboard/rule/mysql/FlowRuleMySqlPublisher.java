package com.alibaba.csp.sentinel.dashboard.rule.mysql;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.FlowRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.service.mysql.FlowRuleService;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
@Component("flowRuleMySqlPublisher")
public class FlowRuleMySqlPublisher implements DynamicRuleMySqlPublisher<List<FlowRuleEntity>> {

    @Resource
    private FlowRuleService flowRuleService;

    @Override
    public void publish(String app, String ip, Integer port, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");

        if (rules == null) {
            return;
        }
        //删除所有的rule
        flowRuleService.remove(new QueryWrapper<FlowRule>().eq(FlowRule.APP, app).eq(FlowRule.IP, ip).eq(FlowRule.PORT, port));
        //新增新的rule
        flowRuleService.saveBatch(convert(rules));

    }

    public List<FlowRule> convert(List<FlowRuleEntity> flowRuleEntityList) {
        ArrayList<FlowRule> flowRuleList = new ArrayList<>();
        if (CollectionUtils.isEmpty(flowRuleEntityList)) {
            return flowRuleList;
        }
        flowRuleEntityList.forEach(item -> {
            FlowRule flowRule = new FlowRule();
            BeanUtils.copyProperties(item, flowRule);
            String s = JSONObject.toJSONString(item.getClusterConfig());
            flowRule.setClusterConfigStr(s);
            flowRule.setGmtCreate(item.getGmtCreate());
            flowRule.setGmtModified(item.getGmtModified());
            flowRuleList.add(flowRule);
        });
        return flowRuleList;
    }
}
