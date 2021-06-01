package com.gxz.gaea.dao.mongodb.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * @author gongxuanzhang
 */
@ConfigurationProperties(prefix = SysMongoConfigProperties.PREFIX)
public class SysMongoConfigProperties extends MongoConfigProperties {

    public static final String PREFIX = "mongodb.sys";

}
