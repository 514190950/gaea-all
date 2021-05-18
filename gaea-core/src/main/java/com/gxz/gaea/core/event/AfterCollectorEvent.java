package com.gxz.gaea.core.event;


import com.gxz.gaea.core.execute.collector.Collector;
import com.gxz.gaea.core.execute.collector.CollectorCombination;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:49
 */
public class AfterCollectorEvent implements Event {

    /**
     * 适配器输入的内容
     */
    private final Object in;

    /**
     * 适配器输出的内容
     */
    private final Object adapt;

    /**
     * 收集器本体
     */
    private final CollectorCombination collector;


    public AfterCollectorEvent(Object in, Object adapt, CollectorCombination collector) {
        this.in = in;
        this.adapt = adapt;
        this.collector = collector;
    }

    public Object getIn() {
        return in;
    }

    public Object getAdapt() {
        return adapt;
    }

    public CollectorCombination getCollector() {
        return collector;
    }
}
