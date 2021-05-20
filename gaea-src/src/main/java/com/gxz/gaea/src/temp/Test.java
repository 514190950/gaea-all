package com.gxz.gaea.src.temp;

import com.gxz.gaea.core.model.CsvData;
import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class Test implements CsvData {

    private String a;



    @Override
    public String toCsv() {
        return "asdf"+a;
    }

    @Override
    public String getHead() {
        return null;
    }
}
