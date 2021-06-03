package com.gxz.gaea.core.execute.trigger;

import com.gxz.gaea.core.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import javax.jms.JMSException;
import javax.jms.Message;
import java.time.Instant;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * ActiveMq的监听实现
 **/
@Slf4j
@Configuration
@EnableJms
public abstract class ActiveMqListenTrigger implements MessageListenTrigger<ActiveMQMessage>, JmsListenerConfigurer {

    /**
     * true为队列  false为订阅 @TODO
     **/
    private boolean queue;

    /**
     * 获得返回的监听位置
     *
     * @return 监听位置
     **/
    public abstract String getDestination();


    /**
     * 触发器iD
     * todo 这里先写死 因为Src默认还是单一触发
     *
     * @return 获得触发器Id
     **/
    public String getId() {
        return "default";
    }


    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId(getId());
        endpoint.setDestination(getDestination());
        endpoint.setMessageListener(this);
        registrar.registerEndpoint(endpoint);
        log.info("{}监听{}",this.getClass().getName(),getDestination());
    }

    /***
     * 真正执行的方法
     * @param message 消息本体
     **/
    @Override
    public void onMessage(Message message) {
        try {
            long jmsTimestamp = message.getJMSTimestamp();
            log.info("ActiveMq[{}]队列中,传递消息，传递时间[{}],执行时间[{}]", this.getDestination(), DateUtils.format(jmsTimestamp),
                    DateUtils.format(Instant.now()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        ActiveMQMessage textMessage = (ActiveMQMessage) message;
        try {
            this.emit(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
