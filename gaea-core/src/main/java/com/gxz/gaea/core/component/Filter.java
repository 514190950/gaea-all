package com.gxz.gaea.core.component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Filter<P> {


    /**
     * 过滤每次解析的内容
     * @param p 解析内容
     * @return 如果返回值为false 触发策略相关
     **/
    boolean filter(P p) throws FilterException;

    // TODO: 2021/6/8  能否把默认策略变成配置?

    default Policy policy(){
        return Policy.STOP;
    }


    /**
     * 当filter 返回值为false的时候触发
     **/
     enum Policy{
         /**
          * 终止过滤
          **/
         STOP,
        /**
         * 继续过滤
         **/
        CONTINUE,
        /**
         * 抛出异常
         **/
        THROW


     }

}
