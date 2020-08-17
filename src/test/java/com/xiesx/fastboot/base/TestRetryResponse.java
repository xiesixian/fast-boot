package com.xiesx.fastboot.base;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

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
}
