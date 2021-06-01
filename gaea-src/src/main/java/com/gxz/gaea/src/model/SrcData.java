package com.gxz.gaea.src.model;

import com.gxz.gaea.core.model.AbstractMetaData;
import com.gxz.gaea.core.model.CsvData;
import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public abstract class SrcData extends AbstractMetaData implements CsvData {


    /**
     * 是否将特殊会话的MAC地址字段转为外层五元组
     */
    protected Boolean macOuter;

    @Override
    public String toCsv() {
        // TODO: 2021/5/28  这里可能要移植一下
      return "a";
    }


}
