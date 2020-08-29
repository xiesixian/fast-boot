package com.xiesx.fastboot.base;

import javax.validation.constraints.NotNull;

import com.xiesx.fastboot.base.TestVo.TestVoValid.B;
import com.xiesx.fastboot.base.TestVo.TestVoValid.C;

import lombok.Data;

@Data
public class TestVo {

    @NotNull(message = "参数不能为空")
    private String id;

    @NotNull(message = "昵称不能为空", groups = B.class)
    private String nickname;

    @NotNull(message = "验证码不能为空", groups = C.class)
    private String code;

    @NotNull(message = "手机不能为空", groups = C.class)
    private String phone;

    public interface TestVoValid {

        public interface A {
        }

        public interface B {
        }

        public interface C {
        }
    }
}
