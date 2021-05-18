package com.gxz.gaea.src.execute;

import com.gxz.gaea.core.execute.analyst.Analyst;
import com.gxz.gaea.core.execute.receive.ActiveMqReceive;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

public class SrcReceive extends ActiveMqReceive<File> {


    @Autowired
    private Analyst<File> analyst;


    @Override
    public Class<File> analysisClass() {
        return File.class;
    }

    @Override
    public Analyst<File> getAnalyst() {
        return analyst;
    }
}
