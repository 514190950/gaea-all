package com.gxz.gaea.src.config;

import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.src.execute.DefaultSrcAnalyst;
import com.gxz.gaea.src.execute.SrcReceive;
import com.gxz.gaea.src.listener.DeleteFileListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@EnableConfigurationProperties(SrcConfig.class)
public class SrcAutoConfiguration {


    @Bean
    public DeleteFileListener deleteFileListener(SrcConfig config){
        return new DeleteFileListener(config);
    }

    @Bean
    public SrcReceive srcReceive(){
        return new SrcReceive();
    }

    @Bean
    @ConditionalOnMissingBean(Analyst.class)
    public Analyst<?> analyst(){
        return new DefaultSrcAnalyst();
    }



}
