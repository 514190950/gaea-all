package com.gxz.gaea.src.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@ConfigurationProperties("src")
@Data
public class SrcConfig {


    private boolean deleteFile = true;

    private boolean backFile;


}
