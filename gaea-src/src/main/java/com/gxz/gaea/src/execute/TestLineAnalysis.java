package com.gxz.gaea.src.execute;

import com.gxz.gaea.src.temp.Test;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class TestLineAnalysis extends SrcLineAnalysis<Test>{


    @Override
    public Test pack(String input) throws Exception {
        Test test = new Test();
        test.setA(input);
        return test;
    }
}
