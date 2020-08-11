package com.xiesx.fastboot.http;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @title BaseResult.java
 * @description 结果基类
 * @author Sixian.xie
 * @date 2020-7-21 22:29:55
 */
@Data
public class TestRetryResponse {

    // 状态
    @JSONField(ordinal = 1)
    private Integer code;

    // 提示
    @JSONField(ordinal = 2)
    private String msg;

    // 数据
    @JSONField(ordinal = 3)
    private Object data;

    // 数据
    @JSONField(ordinal = 3)
    private Boolean success;
}
