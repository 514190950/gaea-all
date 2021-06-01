package com.gxz.gaea.src.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@ConfigurationProperties("gaea.src")
@Data
public class SrcEnvironment {

    /**
     * src当前模块
     **/
    private String protocol;


    /**
     * src层是否删除源文件
     **/
    private boolean deleteFile = true;

    /**
     * src层是否备份源文件
     **/
    private boolean backFile;


    private int outPutMaxLine = 30000;

    /**
     * 当文件行数少于此值时  强制使用单线程执行
     **/
    private int serialMax = 10000;

    /**
     * 多线程数  当小于等于1时视为单线程
     **/
    private int executor = 0;

}
