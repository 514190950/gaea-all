package com.gxz.gaea.core.execute.receive;


import org.apache.activemq.command.ActiveMQMessage;

/**
 * 消息队列消息接收器
 *
 * @author gxz gongxuanzhang@foxmail.com
 */
public abstract class ActiveMqReceive<A> extends MessageReceive<ActiveMQMessage,A> {


}
