package com.gxz.gaea.core.execute.trigger;

import com.gxz.gaea.core.execute.receive.Receiver;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 执行的触发器
 **/
public interface Trigger<Bullet> {

    /**
     * @return 触发策略
     **/
    TriggerPolicy getPolicy();

    /***
     * 执行触发
     * @param bullet 如果是监听  得到的将是监听到的信息内容
     **/
    void emit(Bullet bullet) throws Exception;

    /***
     * 触发器触发的接收器
     * @return 触发的接收器
     **/
    Receiver<Bullet> getReceive();


}
