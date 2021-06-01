package com.gxz.gaea.src.config;

import com.gxz.gaea.core.config.GaeaProperty;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz
 * @date 2021/1/14 0:47
 */
public class SrcPropertyFilePathConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> nodeMap = new HashMap<>();
        propertySources.addLast(new GaeaProperty("gaeaNode", nodeMap));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }

}
