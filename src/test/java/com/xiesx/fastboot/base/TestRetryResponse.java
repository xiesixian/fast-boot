package com.xiesx.fastboot.base;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class TestRetryResponse {

    @JSONField(ordinal = 1)
    private Integer code;

    @JSONField(ordinal = 2)
    private String msg;

    @JSONField(ordinal = 3)
    private Object data;

    @JSONField(ordinal = 3)
    private Boolean success;
}
