package com.gxz.gaea.core.event;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:49
 */
public class AfterFreeEvent implements Event {

    private final Object in;


    public AfterFreeEvent(Object in) {
        this.in = in;
    }

    public Object getIn() {
        return in;
    }
}
