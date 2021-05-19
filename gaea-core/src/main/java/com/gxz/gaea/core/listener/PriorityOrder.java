package com.gxz.gaea.core.listener;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 排序规则见 {@link LinstenrSorter}
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface PriorityOrder {

    /**
     * value越小  优先级越高
     */
    int value() default Integer.MAX_VALUE;

}
