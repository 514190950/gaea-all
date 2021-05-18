package com.gxz.gaea.core.execute.collector;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 收集器  负责获取输入内容
 * Input为输入类型
 * OutPut为输出类型
 */
public interface Collector<Input, OutPut> {

    /**
     * 负责接收数据流交给后面
     *
     * @param input 数据内容
     * @return 返回内容 可以为null
     */
    OutPut collect(Input input);

    /**
     * 如果collect方法的返回值是null  是否终止此次输入程序执行
     *
     * @return true将终止程序执行 默认为true
     */
    default boolean stop() {
        return true;
    }

}
