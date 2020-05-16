package com.xiesx.springboot.core.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.fastjson.JSONException;
import com.github.rholder.retry.RetryException;
import com.xiesx.springboot.base.result.BaseResult;
import com.xiesx.springboot.base.result.R;
import com.xiesx.springboot.support.validate.ValidatorHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @title GlobalExceptionHandle.java
 * @description 全局配置异常处理，分为3种涵盖日常使用场景
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:15:40
 */
@Slf4j
@RestControllerAdvice
public class BaseRestExceptionAdvice {

    /**
     * 系统
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class})
    public BaseResult systemException(HttpServletRequest request, Exception e) {
        log.error("systemException ......", e);
        String msg = "";
        if (e instanceof HttpMessageNotReadableException) {
            msg = "当前参数解析失败";// 400 - Bad Request
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            msg = "不支持当前请求方法";// 405 - Method Not Allowed
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            msg = "不支持当前媒体类型";// 415 - Unsupported Media Type
        } else {
            msg = "未知系统异常";
        }
        return R.error(msg);
    }

    /**
     * 效验
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public BaseResult validatorException(HttpServletRequest request, Exception e) {
        log.error("validatorException ......", e);
        BaseResult result = R.error("参数效验错误");
        // 这里走的是Spring Violation 验证 --> Java Violation，这里有BindException接收
        if (e instanceof BindException) {
            BindingResult violations = ((BindException) e).getBindingResult();
            List<String> errorMessages = new ArrayList<String>();
            for (FieldError fieldError : violations.getFieldErrors()) {
                errorMessages.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
            }
            result.setData(errorMessages);
        }
        // 这里走的是Hibernate Violation 验证 --> Java
        // Violation，这里有ConstraintViolationException接收
        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            List<String> errorMsg = ValidatorHelper.extractPropertyAndMessageAsList(violations);
            result.setData(errorMsg);
        }
        return result;
    }

    /**
     * 数据库
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public BaseResult jdbcException(HttpServletRequest request, Exception e) {
        log.error("jdbcException ......", e);
        String msg = "";
        if (e instanceof EmptyResultDataAccessException) {
            msg = "无数据";// 400 - Bad Request
        } else {
            msg = "未知数据异常";
        }
        return R.error(msg);
    }

    /**
     * 运行
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({RuntimeException.class, IOException.class, JSONException.class, SQLException.class,
            ExecutionException.class, RetryException.class})
    public BaseResult runtimeException(HttpServletRequest request, Exception e) {
        log.error("runtimeException ......", e);
        return R.error(e.getMessage());
    }

    /**
     * 自定义
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({RunException.class})
    public BaseResult runException(HttpServletRequest request, RunException e) {
        log.error("runException ......", e);
        return R.error(e.getErrorCode(), e.getMessage());
    }
}
