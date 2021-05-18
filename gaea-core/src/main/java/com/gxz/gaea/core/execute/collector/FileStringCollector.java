package com.gxz.gaea.core.execute.collector;


import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:17
 * 把文件名换成文件
 */
public class FileStringCollector implements Collector<String, File> {


    @Override
    public File collect(String s) {
        File file = new File(s);
        return file.exists() ? file : null;
    }
}
