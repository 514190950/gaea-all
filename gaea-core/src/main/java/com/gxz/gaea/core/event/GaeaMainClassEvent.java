package com.gxz.gaea.core.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 在Gaea启动类启动的时候
 * 可以通过此事件配置
 **/
public class GaeaMainClassEvent extends SpringApplicationEvent {
    public GaeaMainClassEvent(SpringApplication application, String[] args) {
        super(application, args);
    }
}
