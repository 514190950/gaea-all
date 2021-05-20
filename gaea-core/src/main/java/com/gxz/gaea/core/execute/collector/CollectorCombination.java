package com.gxz.gaea.core.execute.collector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 适配器的组合
 * 所有是配置都将注册到这里
 * 提供默认的适配器
 * 根据类型自动选择对应的适配器
 **/
@Slf4j
@SuppressWarnings("unchecked,rawtypes")
public class CollectorCombination implements ApplicationContextAware {

    private Map<ClassProperty, Collector> combination = new HashMap<>();

    private Map<ClassProperty, Collector> cache = new HashMap<>();


    public CollectorCombination() {
        this.register(new FileStringCollector());
        this.register(new TextMessageStringCollector());
        this.register(new TextMessageFileCollector());
    }


    public <Input, Output> Output adapt(Input input, Class<Output> outputClass) throws CollectorException {
        Collector inputAdapter = matchCollector(input, outputClass);
        if (inputAdapter == null) {
            throw new IllegalArgumentException(
                    "不支持由" + input.getClass().getName() + "到" + outputClass.getName() + "的适配");
        }
        if (log.isTraceEnabled()) {
            log.trace("使用{}解析参数", inputAdapter.getClass().getName());
        }
        return (Output) inputAdapter.collect(input);
    }

    private <Input, Output> Collector matchCollector(Input input, Class<Output> outputClass) {
        ClassProperty cacheKey = new ClassProperty(input.getClass(), outputClass);
        Collector collector = cache.get(cacheKey);
        if (collector != null) {
            return collector;
        }
        for (ClassProperty classProperty : combination.keySet()) {
            if (classProperty.match(input, outputClass)) {
                collector = combination.get(classProperty);
                cache.put(cacheKey, collector);
                return collector;
            }
        }
        return null;


    }

    public void register(Collector collector) {
        ClassProperty handle = handle(collector.getClass());
        combination.put(handle, collector);
    }


    private ClassProperty handle(Class<? extends Collector> inputAdapterClass) {
        Type[] types = inputAdapterClass.getGenericInterfaces();
        for (Type type : types) {
            if (isInputAdapterType(type)) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                return new ClassProperty((Class<?>) actualTypeArguments[0], (Class<?>) actualTypeArguments[1]);
            }
        }
        throw new IllegalArgumentException("无法解析出 ClassProperty");

    }

    private boolean isInputAdapterType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return rawType == Collector.class;
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Collector> beansOfType = applicationContext.getBeansOfType(Collector.class);
        beansOfType.forEach((name, collector) -> register(collector));
    }


    @Data
    @AllArgsConstructor
    public static class ClassProperty {
        private Class<?> firstClass;
        private Class<?> secondClass;

        public <Input, Output> boolean match(Input input, Class<Output> outputClass) {
            return firstClass.isAssignableFrom(input.getClass()) &&
                    secondClass.isAssignableFrom(outputClass);

        }
    }


}
