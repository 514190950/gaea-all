package com.gxz.gaea.core.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 初始化Gaea环境
 **/

public class GaeaHomeInjection {

    public static void inject(ConfigurableEnvironment environment) {
        // 注册gaea 环境
        MutablePropertySources propertySources = environment.getPropertySources();
        GaeaProperty gaeaProperty = assemblyGaeaProperty(environment);
        propertySources.addLast(gaeaProperty);
    }

    private static GaeaProperty assemblyGaeaProperty(ConfigurableEnvironment environment) {

        Map<String, Object> srcProperty = new HashMap<>();
        String gaeaHome = environment.getProperty("gaea.home");
        String configPath = environment.getProperty("gaea.config-path");
        String logPath = environment.getProperty("gaea.log-path");
        String dataPath = environment.getProperty("gaea.data-path");
        System.out.println(System.getProperty("user.dir"));
        if (!StringUtils.hasText(gaeaHome)) {
            gaeaHome = new File(System.getProperty("user.dir")).getParent();
            srcProperty.put("gaea.home", gaeaHome);
        }
        if (!StringUtils.hasText(configPath)) {
            configPath = gaeaHome + File.separator + "config";
            srcProperty.put("gaea.config-path", configPath);
        }

        if (!StringUtils.hasText(logPath)) {
            logPath = gaeaHome + File.separator + "log";
            srcProperty.put("gaea.log-path", logPath);
        }
        if (!StringUtils.hasText(dataPath)) {
            dataPath = gaeaHome + File.separator + "data";
            srcProperty.put("gaea.config-path", dataPath);
        }

        String dataWarehousePath = dataPath + File.separator + "dataWareHouse" + File.separator;

        srcProperty.put("gaea.data-warehouse-csv-path", dataWarehousePath + "csv" + File.separator);
        srcProperty.put("gaea.data-warehouse-json-path", dataWarehousePath + "json" + File.separator);
        return new GaeaProperty("gaea-property", srcProperty);

    }


}
