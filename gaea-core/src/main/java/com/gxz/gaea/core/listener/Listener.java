package com.gxz.gaea.core.listener;


import com.gxz.gaea.core.event.Event;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/5/16 16:37
 */
public interface Listener<E extends Event> {

    void listen(E e);
}
