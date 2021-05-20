package com.gxz.gaea.core.component;


import java.util.Comparator;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 组件核心排序规则
 * 以{@link PriorityOrder} 和{@link com.gxz.gaea.core.component.annotation.PriorityOrder}第一梯队
 * 以{@link Order} 和{@link com.gxz.gaea.core.component.annotation.Order}第二梯队
 * 第一梯队或者第二梯队  以接口优先  注解其次
 * value值越小 优先级越高
 * 其他转为自然排序
 **/
public class GaeaComponentSorter implements Comparator<Object> {

    private static GaeaComponentSorter INSTANCE = new GaeaComponentSorter();

    public static GaeaComponentSorter getInstance() {
        return INSTANCE;
    }

    private GaeaComponentSorter(){

    }


    private static int CONTINUE = -2;

    @Override
    public int compare(Object o1, Object o2) {
        int i = prioritySort(o1, o2);
        if (i != CONTINUE) {
            return i;
        }
        i = orderSort(o1, o2);
        if (i != CONTINUE) {
            return i;
        }
        return defaultSort(o1, o2);


    }

    private int defaultSort(Object o1, Object o2) {
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        }
        return Integer.compare(o1.hashCode(), o2.hashCode());
    }


    private int orderSort(Object o1, Object o2) {
        boolean o1order = isOrder(o1);
        boolean o2order = isOrder(o2);
        if (o1order && o2order) {
            return Integer.compare(orderValue(o1), orderValue(o1));
        }
        if (!o1order && !o2order) {
            return CONTINUE;
        }
        return o1order ? -1 : 1;
    }

    private int orderValue(Object o) {
        if (o instanceof Order) {
            return ((Order) o).getOrder();
        }
        return o.getClass().getAnnotation(com.gxz.gaea.core.component.annotation.Order.class).value();

    }


    private int prioritySort(Object o1, Object o2) {
        boolean o1priority = isPriority(o1);
        boolean o2priority = isPriority(o2);
        if (o1priority && o2priority) {
            return Integer.compare(priorityValue(o1), priorityValue(o1));
        }
        if (!o1priority && !o2priority) {
            return CONTINUE;
        }
        return o1priority ? -1 : 1;
    }

    private int priorityValue(Object o) {
        if (o instanceof PriorityOrder) {
            return ((PriorityOrder) o).getOrder();
        }
        return o.getClass().getAnnotation(com.gxz.gaea.core.component.annotation.PriorityOrder.class).value();

    }

    private boolean isPriority(Object o) {
        return o instanceof PriorityOrder ||
                o.getClass().isAnnotationPresent(com.gxz.gaea.core.component.annotation.PriorityOrder.class);
    }

    private boolean isOrder(Object o) {
        return o instanceof Order ||
                o.getClass().isAnnotationPresent(com.gxz.gaea.core.component.annotation.Order.class);
    }


}
