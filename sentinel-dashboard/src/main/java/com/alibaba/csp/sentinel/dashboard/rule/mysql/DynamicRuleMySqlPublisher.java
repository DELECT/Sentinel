package com.alibaba.csp.sentinel.dashboard.rule.mysql;

/**
 * @Author xingyu
 * @Date 9:14 2022/8/8
 **/
public interface DynamicRuleMySqlPublisher<T> {

    void publish(String app, String ip, Integer port, T rules) throws Exception;

}
