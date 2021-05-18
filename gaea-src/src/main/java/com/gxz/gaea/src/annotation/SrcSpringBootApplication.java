package com.gxz.gaea.src.annotation;

import com.gxz.gaea.src.execute.SrcReceive;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@Import(SrcReceive.class)
public @interface SrcSpringBootApplication {

}
