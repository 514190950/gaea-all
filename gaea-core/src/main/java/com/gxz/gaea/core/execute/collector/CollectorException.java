package com.gxz.gaea.core.execute.collector;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class CollectorException extends Exception {


    private Collector<?, ?> collector;

    private Object in;

    public CollectorException(Collector<?, ?> collector, Object in, String message) {
        super(message);
        this.in = in;
        this.collector = collector;
    }
}
