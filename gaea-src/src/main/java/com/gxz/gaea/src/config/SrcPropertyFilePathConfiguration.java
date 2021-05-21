package com.gxz.gaea.src.config;

import com.gxz.gaea.core.config.GaeaProperty;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz
 * @date 2021/1/14 0:47
 */
public class SrcPropertyFilePathConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println(1);
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> nodeMap = new HashMap<>();
        nodeMap.put("gaea.a","手动注入的");
        propertySources.addLast(new GaeaProperty("gaeaNode", nodeMap));
        String property = environment.getProperty("a.a");
        System.out.println(property+" 这是我改看到的");


      /*  ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String nodeHome = environment.getProperty("node.home");
        String nodeName = environment.getProperty("node.name");
        String srcPath = environment.getProperty("node.src-path");
        String appName = environment.getProperty("spring.application.name");
        if (nodeHome == null) {
            return;
        }
        Map<String, Object> nodeMap = new HashMap<>();
        nodeMap.put("gaea.node-name", nodeName);
        nodeMap.put("gaea.application-name", appName);
        try {
            String layer = appName.split("_")[0];
            String category = appName.split("_")[1];
            nodeMap.put("gaea.category", category);
            nodeMap.put("gaea.layer", layer);
        } catch (Exception e) {
            throw new RuntimeException("加载applicationName失败  请检查[spring.application.name]配置,需要使用[层_模块名]");
        }

        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("gaea.src-path", srcPath);
        fileMap.put("gaea.home-path", nodeHome);


        // home下
        fileMap.put("gaea.config-path", nodeHome + File.separator + "config" + File.separator);
        fileMap.put("gaea.log-path", nodeHome + File.separator + "log" + File.separator);
        String dataPath = nodeHome + File.separator + "data" + File.separator;
        fileMap.put("gaea.data-path", dataPath);
        // data下
        String dataWarehouse = dataPath + File.separator + "dataWarehouse" + File.separator;
        fileMap.put("gaea.data-warehouse-path", dataWarehouse);
        String cache = dataPath + File.separator + "cache" + File.separator;
        fileMap.put("gaea.cache-path", cache);
        fileMap.put("gaea.bak-path", dataPath + File.separator + "bak" + File.separator);
        // dataWarehouse下
        fileMap.put("gaea.data-warehouse-csv-path", dataWarehouse + File.separator + "csv" + File.separator);
        String jsonPath = dataWarehouse + File.separator + "json" + File.separator;
        fileMap.put("gaea.data-warehouse-json-path", jsonPath);
        fileMap.put("gaea.data-warehouse-custom-path", dataWarehouse + File.separator + "custom" + File.separator);
        // json下
        fileMap.put("gaea.cer-chain-path", jsonPath + File.separator + "cerChain" + File.separator);
        fileMap.put("gaea.session-adjust-path", jsonPath + File.separator + "sessionadjust" + File.separator);
        fileMap.put("gaea.alarm-material-path", jsonPath + File.separator + "alarmMaterial" + File.separator);
        // cache下
        fileMap.put("gaea.event-data-path", cache + File.separator + "eventData" + File.separator);
        FileUtils.checkPath(fileMap.values().stream().map(Object::toString).toArray(String[]::new));
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(new GaeaProperty("gaeaNode", nodeMap));
        propertySources.addLast(new GaeaProperty("gaeaFile", fileMap));*/

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }

}
