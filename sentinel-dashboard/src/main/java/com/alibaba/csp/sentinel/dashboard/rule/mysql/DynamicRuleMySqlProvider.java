package com.alibaba.csp.sentinel.dashboard.rule.mysql;

/**
 * @Author xingyu
 * @Date 9:14 2022/8/8
 **/
public interface DynamicRuleMySqlProvider<T> {

    T getRules(String appName, String ip, Integer port) throws Exception;
}
