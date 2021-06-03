package com.gxz.gaea.core.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * Gaea Spring 应用
 **/
public class GaeaApplication extends SpringApplication {


    public GaeaApplication(Class<?>... primarySources) {
        super(primarySources);
    }


    public static ConfigurableApplicationContext srcRun(Class<?> primarySource, String... args) {
        GaeaApplication gaeaApplication = new GaeaApplication(primarySource);
        gaeaApplication.setApplicationContextClass(GaeaApplicationContext.class);
        return gaeaApplication.run(args);
    }


}
