package com.xiesx.fastboot.base;

import com.xiesx.fastboot.base.TestVoValid.A;
import com.xiesx.fastboot.support.validate.annotation.VJson;

import lombok.Data;

@Data
public class TestVo {

    @VJson(message = "这个不是json", groups = {A.class})
    private String code;
}
