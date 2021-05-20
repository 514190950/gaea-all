package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.execute.receive.Receiver;
import com.gxz.gaea.core.execute.trigger.ActiveMqListenTrigger;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class TestMessageTrigger extends ActiveMqListenTrigger {


    private final String destination;

    @Autowired
    private Receiver<ActiveMQMessage> receiver;

    public TestMessageTrigger(String destination) {
        this.destination = destination;
    }

    @Override
    public String getDestination() {
        return destination;
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
