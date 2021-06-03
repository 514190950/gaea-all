package com.gxz.gaea.core.bootstrap;

import com.gxz.gaea.core.config.GaeaHomeInjection;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Gaea Spring 容器上下文
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class GaeaApplicationContext extends AnnotationConfigApplicationContext {


    @Override
    protected void initPropertySources() {
        ConfigurableEnvironment environment = getEnvironment();
        GaeaHomeInjection.inject(environment);
    }


}
