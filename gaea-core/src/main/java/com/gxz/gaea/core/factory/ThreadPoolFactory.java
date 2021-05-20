package com.gxz.gaea.core.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ThreadPoolFactory {

    private final static int CPU = Runtime.getRuntime().availableProcessors();

    public static ThreadPoolExecutor createThreadPool() {
        return new ThreadPoolExecutor(
                CPU + 1,
                CPU * 2,
                10,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(4096),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
