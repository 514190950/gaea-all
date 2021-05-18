package com.gxz.gaea.core.execute.analyst;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 解析器
 */
public interface Analyst<In> {


    /**
     * 解析
     *
     * @param in in
     */
    void analysis(In in);


    /**
     * 最后处理
     *
     * @param in in
     */
    void free(In in);

}
