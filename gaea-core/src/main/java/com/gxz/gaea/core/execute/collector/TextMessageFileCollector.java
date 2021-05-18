package com.gxz.gaea.core.execute.collector;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 解析TextMessage到String
 **/
public class TextMessageFileCollector implements Collector<TextMessage, File> {
    @Override
    public File collect(TextMessage textMessage) {
        try {
            File file = new File(textMessage.getText());
            if(file.exists()){
                return file;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}
