package com.gxz.gaea.dao.mongodb.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
@Conditional(SysMongoTemplateAutoConfigurationCondition.class)
@EnableConfigurationProperties(SysMongoConfigProperties.class)
@Slf4j
public class SysMongoTemplateAutoConfiguration {

    @Autowired
    private SysMongoConfigProperties properties;

    @Bean
    @Primary
    public MongoDbFactory sysFactory() {
        log.info("load pro host:[{}],database:[{}]", properties.getServerAddress(), properties.getDatabase());
        MongoClientSettings set = MongoClientSettings.builder()
                .credential(properties.getMongoCredential())
                .applyToClusterSettings(properties.getBlock()).build();
        return new SimpleMongoClientDbFactory(MongoClients.create(set), properties.getDatabase());
    }

    @Bean
    @Primary
    public MongoTemplate sysMongoTemplate() {
        MongoTemplate sysMongoTemplate = new MongoTemplate(sysFactory());
        MongoConverter proConverter = sysMongoTemplate.getConverter();
        if (proConverter.getTypeMapper().isTypeKey("_class")) {
            ((MappingMongoConverter) proConverter).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        return sysMongoTemplate;
    }

}


class SysMongoTemplateAutoConfigurationCondition implements Condition {

    private static final String PROPERTY_KEY = "mongodb.sys.host";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().containsProperty(PROPERTY_KEY);
    }
}
