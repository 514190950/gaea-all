package com.gxz.gaea.core.listener;


import com.gxz.gaea.core.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:56
 * 所有监听器都注册进入此类
 */
@Slf4j
public class ListenerManager implements ApplicationContextAware {


    /**
     * 这个Map不存储Event的监听器
     **/
    private Map<Class<? extends Event>, List<Listener<Event>>> listenerMap;

    /**
     * 存储多个监听内容
     **/
    private List<Listener<Event>> allEventListeners;

    public void publish(Event event) {
        if (listenerMap == null) {
            return;
        }
        List<Listener<Event>> listeners = listenerMap.get(event.getClass());
        if (!CollectionUtils.isEmpty(listeners)) {
            for (Listener<Event> listener : listeners) {
                listener.listen(event);
            }
        }
        if(!CollectionUtils.isEmpty(allEventListeners)){
            for (Listener<Event> eventListener : allEventListeners) {
                eventListener.listen(event);
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Listener> beansOfType = applicationContext.getBeansOfType(Listener.class);
        if (CollectionUtils.isEmpty(beansOfType)) {
            return;
        }
        listenerMap = new HashMap<>(8);
        allEventListeners = new ArrayList<>();
        beansOfType.forEach((k, v) -> {
            Class<? extends Event> eventGeneric = getEventGeneric(v.getClass());
            if(eventGeneric == Event.class){
                allEventListeners.add(v);
            }else{
                listenerMap.computeIfAbsent(eventGeneric, (key) -> new ArrayList<>()).add(v);
            }
            if (log.isTraceEnabled()) {
                log.trace("register listener {}", v.getClass());
            }
        });

    }

    /***
     *
     * @param listenerClass 出入监听器的Class
     * @return 返回监听的事件 如果返回null 说明没写泛型 默认为Event
     **/
    private Class<? extends Event> getEventGeneric(Class<?> listenerClass) {
        Type[] types = listenerClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterized = (ParameterizedType) type;
                Class<?> rowClass = (Class<?>) parameterized.getRawType();
                if (rowClass == Listener.class) {
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    return (Class<? extends Event>) actualTypeArguments[0];
                }
            }
        }
        return Event.class;
    }


}
