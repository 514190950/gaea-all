package com.gxz.gaea.core.component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 可排序的监听器
 * 监听器排序
 * 排序规则见 {@link GaeaComponentSorter}
 **/

public interface Order {
    int getOrder();
}
