package com.gxz.gaea.src.listen;

import com.gxz.gaea.core.event.AfterFreeEvent;
import com.gxz.gaea.core.listener.Listener;
import com.gxz.gaea.src.config.SrcConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class DeleteFileListener implements Listener<AfterFreeEvent> {

    private final boolean delete;

    private final boolean back;

    public DeleteFileListener(SrcConfig config) {
        this.delete = config.isDeleteFile();
        this.back = config.isBackFile();
    }

    @Override
    public void listen(AfterFreeEvent afterFreeEvent) {
        if(back){
            // TODO: 2021/5/18
            System.out.println("备份文件");
        }
        if (delete) {
            File in = (File) afterFreeEvent.getIn();
            log.info("删除[{}]{}", in.getAbsolutePath(), in.delete() ? "成功" : "失败");
        }


    }

}
