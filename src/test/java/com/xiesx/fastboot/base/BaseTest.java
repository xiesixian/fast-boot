package com.xiesx.fastboot.base;

import java.util.UUID;

import org.junit.Before;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * @title BaseTest.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:16:27
 */
public abstract class BaseTest {

    protected static final Equals SUCCESS = new Equals(200);

    protected static final Equals BAD_REQUEST = new Equals(400);

    protected static final Equals DUPLICATE = new Equals(302);

    protected static final Equals MODIFIED = new Equals(409);

    /**
     * get
     *
     * @param httpUrl
     * @return
     */
    public static Response httpGet(String httpUrl) {
        return RestAssured.given().request().when().get(httpUrl).then().extract().response();
    }

    /**
     * post
     *
     * @param httpUrl
     * @param parm
     * @return
     */
    public static Response httpPost(String httpUrl, JSONObject parm) {
        return RestAssured.given()
                .contentType("application/json")
                .request()
                .body(parm == null ? null : parm.toJSONString())
                .when()
                .post(httpUrl)
                .then()
                .extract()
                .response();
    }

    @Value("${local.server.port}")
    int port;// 本地端口

    @Value("${server.servlet.context-path}")
    String path;

    @Before
    public void doBefore() {
        // RestAssured.baseURI= "http://192.168.3.151";
        RestAssured.port = port;// 端口来访问
        RestAssured.basePath = path;// xxx
    }

    protected String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}