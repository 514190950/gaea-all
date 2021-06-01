package com.gxz.src;

import com.gxz.gaea.src.annotation.GaeaSrc;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@GaeaSrc(destination = "test", module = "测试")
public class TestSrcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TestSrcApplication.class, args);

    }
}
