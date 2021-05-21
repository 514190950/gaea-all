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
    private String module;


    /**
     * src层是否删除源文件
     **/
    private boolean deleteFile = true;

    /**
     * src层是否备份源文件
     **/
    private boolean backFile;


}