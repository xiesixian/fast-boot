package com.xiesx.springboot.core.body;

import java.util.Arrays;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.xiesx.springboot.base.pagination.PaginationResult;
import com.xiesx.springboot.base.result.BaseResult;
import com.xiesx.springboot.base.result.R;

import lombok.extern.slf4j.Slf4j;

/**
 * @title BaseResBodyAdvice.java
 * @description 返回增强
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:14:28
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
public class GlobalBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * systemException --> GlobalExceptionHandle.java 31行
     */
    String[] methodNames = {"requestException"};

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
        } else if (returnValue instanceof JSON) {
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
