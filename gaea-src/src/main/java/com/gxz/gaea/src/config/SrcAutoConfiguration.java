package com.gxz.gaea.src.config;

import com.gxz.gaea.src.execute.SrcReceive;
import com.gxz.gaea.src.listen.DeleteFileListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
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




}
