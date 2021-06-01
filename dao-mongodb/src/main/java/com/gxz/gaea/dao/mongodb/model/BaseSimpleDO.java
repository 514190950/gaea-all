package com.gxz.gaea.dao.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

/**
 * @author gxz
 * @date 2019/12/11 9:49
 **/

@Data
public abstract class BaseSimpleDO  {

    @Id
    protected String id;


}
