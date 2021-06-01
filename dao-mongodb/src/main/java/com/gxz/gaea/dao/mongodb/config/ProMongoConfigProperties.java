package com.gxz.gaea.dao.mongodb.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gongxuanzhang
 */
@ConfigurationProperties(prefix = ProMongoConfigProperties.PREFIX)
public class ProMongoConfigProperties extends MongoConfigProperties {

    public static final String PREFIX = "mongodb.pro";

}
