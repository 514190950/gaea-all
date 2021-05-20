package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.component.Filter;
import com.gxz.gaea.core.model.CsvData;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class TestFilter implements Filter<CsvData> {
    @Override
    public boolean filter(CsvData o) {
        System.out.println("我过滤器！就看看拼出的来的东西是啥" + o);
        return true;
    }
}
