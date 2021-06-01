package com.gxz.gaea.core.component;

import cn.hutool.core.io.FileUtil;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SimpleFileAppender implements FileAppender {

    private final int writeMaxLines;

    private final List<String> lines;

    private final File file;


    /**
     * @param writeMaxLines 行数阈值  当行数达到此值 自动输出文件
     * @param supplier      文件提供接口  可以使用lambda
     * @throws IOException 文件无法创建或者出现了IO问题
     **/
    public SimpleFileAppender(int writeMaxLines, Supplier<File> supplier) throws IOException {
        this(writeMaxLines, supplier.get());
    }

    /**
     * @param writeMaxLines 行数阈值  当行数达到此值 自动输出文件
     * @param file          提供文件
     **/
    public SimpleFileAppender(int writeMaxLines, File file) throws IOException {
        this.writeMaxLines = writeMaxLines;
        this.lines = new ArrayList<>();
        this.file = new File(file.getAbsolutePath());
        if (!this.file.exists() && !this.file.getParentFile().mkdirs() && !this.file.createNewFile()) {
            if (!file.exists()) {
                throw new IOException(file.getAbsolutePath() + "文件无法创建");
            }
        }
    }

    @Override
    public void add(String line) {
        this.lines.add(line);
        if (this.lines.size() >= writeMaxLines) {
            write();
        }
    }


    @Override
    public void write() {
        if (this.lines.isEmpty()) {
            return;
        }
        FileUtil.appendUtf8Lines(this.lines, file);
        this.lines.clear();
    }


    @Override
    public void close() {
        this.write();
    }


}
