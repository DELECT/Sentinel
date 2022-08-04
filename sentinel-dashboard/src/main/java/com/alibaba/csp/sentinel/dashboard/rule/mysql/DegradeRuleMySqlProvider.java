package com.alibaba.csp.sentinel.dashboard.rule.mysql;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.mysql.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.service.mysql.DegradeRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("degradeRuleMySqlProvider")
public class DegradeRuleMySqlProvider implements DynamicRuleMySqlProvider<List<DegradeRuleEntity>> {

    @Resource
    DegradeRuleService degradeRuleService;

    @Override
    public List<DegradeRuleEntity> getRules(String appName, String ip, Integer port) throws Exception {
        //查询所有的熔断规则
        List<DegradeRule> list = degradeRuleService.list(new QueryWrapper<DegradeRule>()
                .eq(DegradeRule.APP, appName)
                .eq(DegradeRule.IP,ip)
                .eq(DegradeRule.PORT,port));

        if (CollectionUtils.isEmpty(list)) {
            new ArrayList<>();
        }
        List<DegradeRuleEntity> degradeRuleEntities = convert(list);

        return degradeRuleEntities;
    }

    public List<DegradeRuleEntity> convert(List<DegradeRule> list) {
        ArrayList<DegradeRuleEntity> degradeRuleEntities = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return degradeRuleEntities;
        }
        list.forEach(item -> {
            DegradeRuleEntity degradeRuleEntity = new DegradeRuleEntity();
            BeanUtils.copyProperties(item, degradeRuleEntity);
            degradeRuleEntity.setGmtCreate(item.getGmtCreate());
            degradeRuleEntity.setGmtModified(item.getGmtModified());
            degradeRuleEntities.add(degradeRuleEntity);
        });
        return degradeRuleEntities;
    }
}
