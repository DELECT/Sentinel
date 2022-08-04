package com.alibaba.csp.sentinel.dashboard.rule.mysql;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.service.mysql.DegradeRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("degradeRuleMySqlPublisher")
public class DegradeRuleMySqlPublisher implements DynamicRuleMySqlPublisher<List<DegradeRuleEntity>> {

    @Resource
    private DegradeRuleService degradeRuleService;

    @Override
    public void publish(String app, String ip, Integer port, List<DegradeRuleEntity> rules) throws Exception {

        //删除所有的熔断规则
        degradeRuleService.remove(new QueryWrapper<DegradeRule>()
                .eq(DegradeRule.APP, app)
                .eq(DegradeRule.IP,ip)
                .eq(DegradeRule.PORT,port));

        //批量插入新的规则
        List<DegradeRule> convert = convert(rules);
        if (!CollectionUtils.isEmpty(convert)) {
            degradeRuleService.saveBatch(convert);
        }
    }
    public List<DegradeRule> convert(List<DegradeRuleEntity> ruleEntities) {
        List<DegradeRule> degradeRules = new ArrayList<>();
        if (CollectionUtils.isEmpty(ruleEntities)) {
            return degradeRules;
        }
        ruleEntities.forEach(item -> {
            DegradeRule degradeRule = new DegradeRule();
            BeanUtils.copyProperties(item, degradeRule);
            degradeRules.add(degradeRule);
        });
        return degradeRules;
    }
}
