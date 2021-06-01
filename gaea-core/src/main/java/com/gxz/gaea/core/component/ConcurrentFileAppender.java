package com.gxz.gaea.core.component;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * @author gxz gongxuanzhang@foxmail.com
 *
 * 此类可以执行[线程安全]的添加和输出文件操作
 * 你可以任意创建线程向此文件不断的add行数
 * 同时可以设置阈值 当文件行数达到阈值的时候，此类会自动将行数写入到指定文件中，此过程也是完全线程安全的，
 * 你可以不用担心线程问题，
 * 但需要注意的是：最终会有一部分未达到阈值的行数未释放，需要手动释放一次   调用write()方法或者close()方法都可以
 * 你也可以把此类创建在try-catch-with 中 可以自动释放行数
 * try(ConcurrentLines c = new ConcurrentLines(100,new File(""))){
 * for(int i =0;i<100000;i++){
 * c.add("arbitary");
 * }
 * }
 **/
public class ConcurrentFileAppender implements FileAppender {

    private final int writeMaxLines;

    private final List<String> lines;

    private final File file;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();


    /**
     * @param writeMaxLines 行数阈值  当行数达到此值 自动输出文件
     * @param supplier      文件提供接口  可以使用lambda
     * @throws IOException 文件无法创建或者出现了IO问题
     **/
    public ConcurrentFileAppender(int writeMaxLines, Supplier<File> supplier) throws IOException {
        this(writeMaxLines, supplier.get());
    }

    /**
     * @param writeMaxLines 行数阈值  当行数达到此值 自动输出文件
     * @param file  提供文件
     **/
    public ConcurrentFileAppender(int writeMaxLines, File file) throws IOException {
        this.writeMaxLines = writeMaxLines;
        this.lines = Collections.synchronizedList(new ArrayList<>());
        this.file = new File(file.getAbsolutePath());
        if (!this.file.exists() && !this.file.getParentFile().mkdirs() && !this.file.createNewFile()) {
            if (!file.exists()) {
                throw new IOException(file.getAbsolutePath() + "文件无法创建");
            }
        }
    }

    @Override
    public void add(String line) {
        readLock.lock();
        try {
            this.lines.add(line);
        } finally {
            readLock.unlock();
        }
        if (this.lines.size() >= writeMaxLines) {
            writeLock.lock();
            try {
                write();
            } finally {
                writeLock.unlock();
            }

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
