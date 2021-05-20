package com.gxz.gaea.core.execute.collector;


import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:17
 * 把文件名换成文件
 */
public class FileStringCollector implements Collector<String, File> {


    @Override
    public File collect(String s) throws CollectorException {
        File file = new File(s);
        if (file.exists()) {
            return file;
        }
        String message = "文件[" + s + "]不存在";
        throw new CollectorException(this, s, message);
    }
}
