package com.xiesx.fastboot.core.body;

import java.util.Arrays;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.xiesx.fastboot.base.pagination.PaginationResult;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;

import lombok.extern.log4j.Log4j2;

/**
 * @title GlobalBodyAdvice.java
 * @description 统一返回
 * @author Sixian.xie
 * @date 2020-7-21 22:30:32
 */
@Log4j2
@RestControllerAdvice
@SuppressWarnings("all")
public class GlobalBodyAdvice implements ResponseBodyAdvice<Object> {

    String[] methodNames = {};

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter, MediaType mediaType, Class clas,
            ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.debug("beforeBodyWrite ......");
        Object res = null;
        // 按需使用，如果有改动，务必兼容之前代码
        if (returnValue instanceof BaseResult || returnValue instanceof PaginationResult) {
            res = returnValue;
        } else if (returnValue instanceof Map<?, ?> || returnValue instanceof Iterable<?>) {
            res = returnValue;
        } else if (returnValue instanceof com.alibaba.fastjson.JSON) {
            res = returnValue;
        } else if (returnValue instanceof String) {
            res = returnValue;
        } else {
            res = R.succ(returnValue);
        }
        return res;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class clas) {
        // 获取当前处理请求的controller的方法
        String methodName = methodParameter.getMethod().getName();
        // 不拦截
        return !Arrays.asList(methodNames).contains(methodName);
    }
}
