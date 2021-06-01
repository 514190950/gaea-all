package com.gxz.src.aa;

import com.gxz.gaea.src.execute.SrcLineAnalyst;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class TestAnalyst extends SrcLineAnalyst<TestModel> {


    @Override
    public TestModel pack(String input) throws Exception {
        String[] split = input.split(",");
        TestModel testModel = new TestModel();
        testModel.setName(split[0]);
        testModel.setCaptureTime(System.currentTimeMillis());
        testModel.setAge(Integer.parseInt(split[1]));
        return testModel;
    }
}
