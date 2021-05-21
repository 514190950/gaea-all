package com.gxz.gaea.core.config;


import com.gxz.gaea.core.execute.collector.CollectorCombination;
import com.gxz.gaea.core.component.ListenerManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
@EnableConfigurationProperties(GaeaEnvironment.class)
public class GaeaListenerManagerAutoConfiguration {

    @Bean
    public ListenerManager listenerManager(){
        return new ListenerManager();
    }

    @Bean
    public CollectorCombination collectorCombination(){
        return new CollectorCombination();
    }




}
