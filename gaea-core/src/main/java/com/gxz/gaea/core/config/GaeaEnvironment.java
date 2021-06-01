package com.gxz.gaea.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gxz gongxuanzhang@foxmail.com
 *
 * 由{@link GaeaHomeInjection}进行默认注入
 **/
@ConfigurationProperties(prefix = "gaea")
@Getter
@Setter
public class GaeaEnvironment {

    /**
     * 当收集器收集到null或者捕获异常的时候是否终止此次之行
     **/
    private boolean collectorStop;

    /**
     * 可指定工作目录  如果不指定
     * 默认当前目录在 ${home}/bin/执行
     **/
    private String home;

    /**
     * 默认 为${home}/config
     **/
    private String configPath;

    /**
     * 默认为 ${home}/log
     **/
    private String logPath;

    /**
     * 默认为 ${home}/data
     **/
    private String dataPath;


    private String nodeName;


    // dataWarehouse下

    private String dataWarehouseCsvPath;

    private String dataWarehouseJsonPath;



}
