package com.gxz.gaea.core.config;


import com.gxz.gaea.core.execute.collector.CollectorCombination;
import com.gxz.gaea.core.listener.ListenerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
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
