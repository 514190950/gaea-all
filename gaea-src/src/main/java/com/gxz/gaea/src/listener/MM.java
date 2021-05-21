package com.gxz.gaea.src.listener;

import com.gxz.gaea.src.annotation.GaeaSrc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MM implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Class<?> mainApplicationClass = event.getSpringApplication().getMainApplicationClass();
        boolean annotationPresent = mainApplicationClass.isAnnotationPresent(GaeaSrc.class);
        SpringApplication springApplication = event.getSpringApplication();

        System.out.println("MM");
    }

}
