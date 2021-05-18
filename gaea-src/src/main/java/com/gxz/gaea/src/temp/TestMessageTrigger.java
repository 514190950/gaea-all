package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.execute.receive.Receiver;
import com.gxz.gaea.core.execute.trigger.ActiveMqListenTrigger;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class TestMessageTrigger extends ActiveMqListenTrigger {


    @Autowired
    private Receiver<ActiveMQMessage> receiver;

    @Override
    public String getDestination() {
        return "test";
    }

    @Override
    public String getId() {
        return "test监听器";
    }


    @Override
    public void emit(ActiveMQMessage message) throws Exception {
        Receiver<ActiveMQMessage> receive = getReceive();
        receive.receive(message);
    }

    @Override
    public Receiver<ActiveMQMessage> getReceive() {
        return receiver;
    }

}
