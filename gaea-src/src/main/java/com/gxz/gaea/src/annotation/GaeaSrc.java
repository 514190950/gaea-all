package com.gxz.gaea.src.annotation;

import com.gxz.gaea.src.config.ImportActiveMqMessageTrigger;
import com.gxz.gaea.src.config.SrcAutoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author gongxuanzhang gongxuanzhang@foxmail.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@Import({ImportActiveMqMessageTrigger.class,
        SrcAutoConfiguration.class})
public @interface GaeaSrc {

    /**
     *
     * src监听的队列位置
     **/
    String destination();

    /**
     * 当前Src模块名称
     **/

    String module();

}
