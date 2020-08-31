package com.xiesx.fastboot.core.fastjson;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiesx.fastboot.FastBootApplication;
import com.xiesx.fastboot.core.fastjson.annotation.GoDesensitized;
import com.xiesx.fastboot.core.fastjson.desensitized.SensitiveTypeEnum;

import lombok.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastBootApplication.class)
public class FastJsonTest {


    @Test
    public void fastjson() {}

    @Data
    public static class TestDto {

        @GoDesensitized(type = SensitiveTypeEnum.CHINESE_NAME)
        @JSONField(ordinal = 1)
        private String name;

        @JSONField(ordinal = 2)
        private Date birthDay;

        @JSONField(ordinal = 3, format = "yyyy-MM-dd")
        private Date registerDay;

        @JSONField(ordinal = 4)
        @GoDesensitized(type = SensitiveTypeEnum.ID_CARD)
        private String idCard;

        @JSONField(ordinal = 5)
        @GoDesensitized(type = SensitiveTypeEnum.BANK_CARD)
        private String bankCard;

        @JSONField(ordinal = 6)
        @GoDesensitized(type = SensitiveTypeEnum.PHONE)
        private String phone;

        @JSONField(ordinal = 7)
        @GoDesensitized(type = SensitiveTypeEnum.MOBILE)
        private String tel;

        @JSONField(ordinal = 8)
        @GoDesensitized(type = SensitiveTypeEnum.ADDRESS)
        private String address;

        @JSONField(ordinal = 9)
        @GoDesensitized(type = SensitiveTypeEnum.EMAIL)
        private String email;

        @JSONField(ordinal = 10)
        @GoDesensitized(type = SensitiveTypeEnum.PASSWORD)
        private String password;

        @JSONField(ordinal = 11)
        @GoDesensitized(type = SensitiveTypeEnum.CARNUMBER)
        private String carnumber;
    }
}
