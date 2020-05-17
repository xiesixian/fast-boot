package com.xiesx.springboot.http;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiesx.springboot.base.BaseTest;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * @title SimpleTest.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:16:35
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes = ZeduApplication.class, webEnvironment =
// SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpTest extends BaseTest {

    /**
     * 传参
     */
    @Test
    public void str() {
        Response response = httpGet("/simple/testParam1?data=1");
        log.info("response {}", response.getBody().asString());
        String data = response.jsonPath().getString("data");
        Assert.assertTrue(data.equals("1"));
    }
}
