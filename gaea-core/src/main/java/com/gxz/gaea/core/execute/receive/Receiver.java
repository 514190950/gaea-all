package com.gxz.gaea.core.execute.receive;


/**
 * gaea的消费者处理逻辑可通过任何方式接受,不同组件解析不同输入。
 * Receiver接口无需知道输入方式，只需要解析即可
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Receiver<In> {


    /**
     * 执行器核心方法 具体执行内容
     *
     * @param in 输入内容
     * @throws Exception 解析过程中可能出现任何错误
     **/
    void receive(In in) throws Exception;



}
