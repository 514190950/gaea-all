package com.gxz.gaea.core.execute.collector;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 解析TextMessage到String
 **/
public class TextMessageStringCollector implements Collector<TextMessage, String> {
    @Override
    public String collect(TextMessage textMessage) {
        try {
            return textMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}
