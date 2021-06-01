package com.gxz.gaea.dao.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * dao层的注入类
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
@Import({
        ProMongoTemplateAutoConfiguration.class,
        SysMongoTemplateAutoConfiguration.class
})
public class MongoDBAutoConfiguration {


}
