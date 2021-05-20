package com.gxz.gaea.src.annotation;

import com.gxz.gaea.src.config.ImportActiveMqMessageTrigger;
import com.gxz.gaea.src.config.SrcAutoConfiguration;
import com.gxz.gaea.src.execute.SrcReceive;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author gongxuanzhang gongxuanzhang@foxmail.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@Import({ImportActiveMqMessageTrigger.class, SrcAutoConfiguration.class})
public @interface SrcSpringBootApplication {

    /**
     *
     * src监听的队列位置
     **/
    String destination();

}
