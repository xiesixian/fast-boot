package com.xiesx.springboot.core.jpa;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public abstract class JpaPlusEntity<T> implements Serializable {

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
