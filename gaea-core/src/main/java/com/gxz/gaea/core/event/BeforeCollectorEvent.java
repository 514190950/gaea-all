package com.gxz.gaea.core.event;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:49
 */
public class BeforeCollectorEvent implements Event {

    private final Object in;


    public BeforeCollectorEvent(Object in) {
        this.in = in;
    }
}
