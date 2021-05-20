package com.gxz.gaea.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@ConfigurationProperties(prefix = "gaea")
@Getter
@Setter
public class GaeaConfig {

    /**
     * 当收集器收集到null或者捕获异常的时候是否终止此次之行
     **/
    private boolean collecorStop;

    private String a;

    public GaeaConfig setA(String a) {
        this.a = a;
        return this;
    }
}
