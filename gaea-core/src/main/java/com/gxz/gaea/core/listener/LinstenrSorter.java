package com.gxz.gaea.core.listener;

import com.gxz.gaea.core.event.Event;

import java.util.Comparator;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class LinstenrSorter implements Comparator<Listener<? extends Event>> {

    @Override
    public int compare(Listener<? extends Event> o1, Listener<? extends Event> o2) {
        int i = prioritySort(o1, o2);
        if(i!=0){
            return i;
        }
        return orderSort(o1,o2);
    }

    private int orderSort(Listener<? extends Event> o1, Listener<? extends Event> o2) {
        boolean o1order = isOrder(o1);
        boolean o2order = isOrder(o2);
        if (o1order && o2order) {
            return Integer.compare(orderValue(o1),orderValue(o1));
        }
        if (!o1order && !o2order) {
            return 0;
        }
        return o1order ? -1 : 1;
    }

    private int orderValue(Listener<? extends Event> listener) {
        if (listener instanceof OrderListener) {
            return ((OrderListener) listener).getOrder();
        }
        return listener.getClass().getAnnotation(Order.class).value();

    }


    private int prioritySort(Listener<? extends Event> o1, Listener<? extends Event> o2) {
        boolean o1priority = isPriority(o1);
        boolean o2priority = isPriority(o2);
        if (o1priority && o2priority) {
            return Integer.compare(priorityValue(o1),priorityValue(o1));
        }
        if (!o1priority && !o2priority) {
            return 0;
        }
        return o1priority ? -1 : 1;
    }

    private int priorityValue(Listener<? extends Event> listener) {
           if(listener instanceof PriorityOrderListener){
               return ((PriorityOrderListener) listener).getOrder();
           }
           return listener.getClass().getAnnotation(PriorityOrder.class).value();

    }

    private boolean isPriority(Listener<? extends Event> listener) {
        return listener instanceof PriorityOrderListener ||
                listener.getClass().isAnnotationPresent(PriorityOrder.class);
    }

    private boolean isOrder(Listener<? extends Event> listener) {
        return listener instanceof OrderListener ||
                listener.getClass().isAnnotationPresent(Order.class);
    }
}
