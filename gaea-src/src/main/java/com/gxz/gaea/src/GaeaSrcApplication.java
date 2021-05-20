package com.gxz.gaea.src;

import com.gxz.gaea.core.config.GaeaConfig;
import com.gxz.gaea.core.execute.receive.Receiver;
import com.gxz.gaea.src.annotation.SrcSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * @author gongxuanzhang
 */
@SrcSpringBootApplication(destination = "jianting")
public class GaeaSrcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GaeaSrcApplication.class, args);
        GaeaConfig bean = run.getBean(GaeaConfig.class);
        System.out.println(bean.getA());
    }

}
