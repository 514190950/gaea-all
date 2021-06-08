package com.gxz.gaea.src.listener;

import cn.hutool.core.io.FileUtil;
import com.gxz.gaea.core.config.GaeaEnvironment;
import com.gxz.gaea.core.event.AfterFreeEvent;
import com.gxz.gaea.core.component.Listener;
import com.gxz.gaea.src.config.SrcEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class DeleteFileListener implements Listener<AfterFreeEvent> {

    private final boolean delete;

    private final boolean back;

    private final String backPath;

    public DeleteFileListener(SrcEnvironment config, GaeaEnvironment environment) {
        this.delete = config.isDeleteFile();
        this.back = config.isBackFile();
        this.backPath = environment.getBackPath();

    }

    @Override
    public void listen(AfterFreeEvent afterFreeEvent) {
        File in = (File) afterFreeEvent.getIn();
        if (back) {
            File backFile = new File(backPath + File.separator + in.getName());
            FileUtil.copy(in.toPath(), backFile.toPath());
            System.out.println("备份文件");
        }
        if (delete) {
            log.info("删除[{}]{}", in.getAbsolutePath(), in.delete() ? "成功" : "失败");
        }


    }

}
