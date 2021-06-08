package com.gxz.gaea.core.execute.collector;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 解析TextMessage到文件
 **/
public class TextMessageFileCollector implements Collector<TextMessage, File> {

    @Override
    public File collect(TextMessage textMessage) throws CollectorException {
        try {
            File file = new File(textMessage.getText());
            if (file.exists()) {
                return file;
            }
            String message = "文件" + file.getAbsolutePath() + "不存在";
            throw new CollectorException(this, textMessage, message);
        } catch (JMSException e) {
            throw new CollectorException(this, textMessage, e.getMessage());
        }

    }
}
