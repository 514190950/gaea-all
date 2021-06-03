package com.gxz.src;

import com.gxz.gaea.core.bootstrap.GaeaApplication;
import com.gxz.gaea.core.config.GaeaEnvironment;
import com.gxz.gaea.src.annotation.GaeaSrc;
import com.gxz.src.aa.GaeaApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@GaeaSrc(destination = "test", module = "测试")
public class TestSrcApplication {

    public static void main(String[] args) {
        GaeaApplication.srcRun(TestSrcApplication.class, args);
    }
}
