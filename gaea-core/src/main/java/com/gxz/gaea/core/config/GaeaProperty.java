package com.gxz.gaea.core.config;

import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * @author gongxuanzhang
 * 配合各种
 **/

public class GaeaProperty extends MapPropertySource {

    public GaeaProperty(String name, Map<String, Object> source) {
        super(name, source);
    }
}
