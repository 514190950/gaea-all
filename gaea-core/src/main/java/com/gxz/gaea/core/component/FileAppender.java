package com.gxz.gaea.core.component;

import java.io.Closeable;

/**
 * @author gxz gongxuanzhang@foxmail.com
 *
 * 可以向文件中写入行数的接口
 *
 **/
public interface FileAppender extends Closeable {

    /**
     * 添加一行
     * @param line 一行
     **/
    void add(String line);

    /**
     * 执行写入
     *
     **/
    void write();

}
