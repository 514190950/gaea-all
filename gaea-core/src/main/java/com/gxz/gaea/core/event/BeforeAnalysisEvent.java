package com.gxz.gaea.core.event;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:39
 */
public class BeforeAnalysisEvent implements Event {

    private final Object in;

    public BeforeAnalysisEvent(Object in) {
        this.in = in;
    }

    public Object getIn() {
        return in;
    }
}
