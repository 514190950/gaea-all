package com.gxz.src.aa;

import com.gxz.gaea.src.model.SrcData;
import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Data
public class TestModel extends SrcData {

    private String name;

    private int age;

    @Override
    public String getHead() {
        return "asdf";
    }

    @Override
    public String toCsv() {
        return toString();
    }
}
