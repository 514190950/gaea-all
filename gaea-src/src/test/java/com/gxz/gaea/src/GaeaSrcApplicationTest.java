package com.gxz.gaea.src;

import com.gxz.gaea.core.execute.receive.Receiver;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GaeaSrcApplicationTest {

    @Autowired
    private Receiver<ActiveMQMessage> receiver;

    @Test
    void a() throws Exception {
        ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        activeMQTextMessage.setText("/Users/gongxuanzhang/Desktop/未命名 2.txt");
        receiver.receive(activeMQTextMessage);
    }

}
