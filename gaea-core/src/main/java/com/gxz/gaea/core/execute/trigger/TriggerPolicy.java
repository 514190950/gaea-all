package com.gxz.gaea.core.execute.trigger;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * 触发器策略
 * 目前支持监听和定时任务两种
 **/
public enum TriggerPolicy {

    /**
     * 监听策略
     **/
    LISTEN,
    /**
     * 定时任务 (目前没实现) @Todo
     **/
    SCHEDULE;

}
