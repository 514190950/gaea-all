package com.gxz.gaea.core.component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Setter
@Getter
public class FilterException extends RuntimeException {

    private final Filter<?> filter;
    private final Object p;

    public <P> FilterException(String message, Filter<P> filter, P p) {
        super(message);
        this.filter = filter;
        this.p = p;

    }


}
