package com.gxz.gaea.core.execute.analyst;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * 行解析器
 * 解析一行记录
 **/
public interface LineAnalyst<OUTPUT> {


    /****
     * 解析一行 封装成实体
     * @param input 一行数据
     * @throws Exception 拼装过程中可能抛出的异常
     * @return T 解析完成的实体
     **/
    OUTPUT pack(String input) throws Exception;

}
