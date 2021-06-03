package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.execute.receive.Receiver;
import com.gxz.gaea.core.execute.trigger.ActiveMqListenTrigger;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * src默认使用ActiveMq作为 触发器
 *
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SrcMessageTrigger extends ActiveMqListenTrigger {


    private final String destination;

    @Autowired
    private Receiver<ActiveMQMessage> receiver;

    public SrcMessageTrigger(String destination) {
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
