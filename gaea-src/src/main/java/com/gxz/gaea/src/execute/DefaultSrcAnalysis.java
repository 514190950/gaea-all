package com.gxz.gaea.src.execute;

import com.gxz.gaea.core.execute.analyst.Analyst;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class DefaultSrcAnalysis implements Analyst<File> {

    @Override
    public void analysis(File file) {
        System.out.println(file.getAbsolutePath());
    }

    @Override
    public void free(File file) {

    }
}
