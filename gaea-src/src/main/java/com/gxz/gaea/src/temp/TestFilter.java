package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.component.Filter;
import com.gxz.gaea.core.model.CsvData;
import com.gxz.gaea.src.config.SrcEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
