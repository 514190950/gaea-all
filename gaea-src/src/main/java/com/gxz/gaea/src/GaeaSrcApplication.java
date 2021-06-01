package com.gxz.gaea.src;

import com.gxz.gaea.src.annotation.GaeaSrc;
import org.springframework.boot.SpringApplication;

/**
 * @author gongxuanzhang
 */
@GaeaSrc(destination = "jianting",module = "这是什么模块")
public class GaeaSrcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaeaSrcApplication.class,args);
    }

}
