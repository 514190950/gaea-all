package com.gxz.gaea.src.listener;

import com.gxz.gaea.core.config.GaeaProperty;
import com.gxz.gaea.src.config.GaeaSrcBanner;
import com.gxz.gaea.src.annotation.GaeaSrc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 把Src注解上的内容注入到容器环境中
 **/

public class GaeaSrcApplicationSpringInjection implements ApplicationListener<ApplicationEnvironmentPreparedEvent>,
        PriorityOrdered {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // 注册gaea 环境
        System.out.println("Src的");
        Class<?> mainApplicationClass = event.getSpringApplication().getMainApplicationClass();
        Assert.state(mainApplicationClass.isAnnotationPresent(GaeaSrc.class),
                "启动类上没有SrcSpringBootApplication注解");
        GaeaSrc annotation = mainApplicationClass.getAnnotation(GaeaSrc.class);
        String module = annotation.module();
        SpringApplication springApplication = event.getSpringApplication();
        springApplication.setBanner(new GaeaSrcBanner(module));
        ConfigurableEnvironment environment = event.getEnvironment();
        Map<String,Object> srcProperty = new HashMap<>();
        srcProperty.put("gaea.src.module",module);
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(new GaeaProperty("src-property", srcProperty));

//
//        String nodeHome = environment.getProperty("node.home");
//        String nodeName = environment.getProperty("node.name");
//        String srcPath = environment.getProperty("node.src-path");
//        String appName = environment.getProperty("spring.application.name");
//        if (nodeHome == null) {
//            return;
//        }
//        Map<String, Object> nodeMap = new HashMap<>();
//        nodeMap.put("gaea.node-name", nodeName);
//        nodeMap.put("gaea.application-name", appName);
//        try {
//            String layer = appName.split("_")[0];
//            String category = appName.split("_")[1];
//            nodeMap.put("gaea.category", category);
//            nodeMap.put("gaea.layer", layer);
//        } catch (Exception e) {
//            throw new RuntimeException("加载applicationName失败  请检查[spring.application.name]配置,需要使用[层_模块名]");
//        }
//
//        Map<String, Object> fileMap = new HashMap<>();
//        fileMap.put("gaea.src-path", srcPath);
//        fileMap.put("gaea.home-path", nodeHome);
//
//
//        // home下
//        fileMap.put("gaea.config-path", nodeHome + File.separator + "config" + File.separator);
//        fileMap.put("gaea.log-path", nodeHome + File.separator + "log" + File.separator);
//        String dataPath = nodeHome + File.separator + "data" + File.separator;
//        fileMap.put("gaea.data-path", dataPath);
//        // data下
//        String dataWarehouse = dataPath + File.separator + "dataWarehouse" + File.separator;
//        fileMap.put("gaea.data-warehouse-path", dataWarehouse);
//        String cache = dataPath + File.separator + "cache" + File.separator;
//        fileMap.put("gaea.cache-path", cache);
//        fileMap.put("gaea.bak-path", dataPath + File.separator + "bak" + File.separator);
//        // dataWarehouse下
//        fileMap.put("gaea.data-warehouse-csv-path", dataWarehouse + File.separator + "csv" + File.separator);
//        String jsonPath = dataWarehouse + File.separator + "json" + File.separator;
//        fileMap.put("gaea.data-warehouse-json-path", jsonPath);
//        fileMap.put("gaea.data-warehouse-custom-path", dataWarehouse + File.separator + "custom" + File.separator);
//        // json下
//        fileMap.put("gaea.cer-chain-path", jsonPath + File.separator + "cerChain" + File.separator);
//        fileMap.put("gaea.session-adjust-path", jsonPath + File.separator + "sessionadjust" + File.separator);
//        fileMap.put("gaea.alarm-material-path", jsonPath + File.separator + "alarmMaterial" + File.separator);
//        // cache下
//        fileMap.put("gaea.event-data-path", cache + File.separator + "eventData" + File.separator);
//        FileUtils.checkPath(fileMap.values().stream().map(Object::toString).toArray(String[]::new));
//        MutablePropertySources propertySources = environment.getPropertySources();
//        propertySources.addLast(new GaeaProperty("gaeaNode", nodeMap));
//        propertySources.addLast(new GaeaProperty("gaeaFile", fileMap));
    }


    @Override
    public int getOrder() {
        return 1;
    }

}
