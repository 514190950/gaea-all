package com.gxz.src.aa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class GaeaApplicationContext extends AnnotationConfigApplicationContext {


    @Override
    protected void initPropertySources() {
        System.out.println("gaea注入内容");
        ConfigurableEnvironment environment = getEnvironment();

    }
}
