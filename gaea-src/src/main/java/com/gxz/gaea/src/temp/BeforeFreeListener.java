package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.event.BeforeFreeEvent;
import com.gxz.gaea.core.listener.Listener;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class BeforeFreeListener implements Listener<BeforeFreeEvent> {

    @Override
    public void listen(BeforeFreeEvent event) {
        System.out.println("free之前");
    }
}
