package com.gxz.gaea.core.execute.trigger;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 监听消息队列的触发器
 **/
public interface MessageListenTrigger<M extends Message> extends Trigger<M> , MessageListener {


    @Override
    default TriggerPolicy getPolicy() {
        return TriggerPolicy.LISTEN;
    }



}
