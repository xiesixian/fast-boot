package com.xiesx.fastboot.support.validate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xiesx.fastboot.FastBootApplication;
import com.xiesx.fastboot.base.TestVo;
import com.xiesx.fastboot.base.TestVoValid.A;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastBootApplication.class)
public class ValidatorHelperTest {

    @Resource
    Validator validator;

    @Test
    public void verify1() {
        TestVo vo = new TestVo();
        Set<ConstraintViolation<TestVo>> constraintViolations = validator.validate(vo, A.class);
        List<String> message = ValidatorHelper.extractMessage(constraintViolations);
        log.info(JSON.toJSONString(message));
    }

    @Test
    public void verify2() {
        TestVo vo = new TestVo();
        try {
            ValidatorHelper.validate(vo, A.class);
        } catch (ConstraintViolationException e) {
            List<String> message = ValidatorHelper.extractMessage(e);
            log.info(JSON.toJSONString(message));

            Map<String, String> message2 = ValidatorHelper.extractPropertyAndMessage(e);
            log.info(JSON.toJSONString(message2));

            List<String> message3 = ValidatorHelper.extractPropertyAndMessageAsList(e);
            log.info(JSON.toJSONString(message3));
        }
    }
}
