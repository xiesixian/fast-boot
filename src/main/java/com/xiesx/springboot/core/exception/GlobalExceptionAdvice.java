package com.xiesx.springboot.core.exception;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.collect.Lists;
import com.xiesx.springboot.base.result.BaseResult;
import com.xiesx.springboot.base.result.R;
import com.xiesx.springboot.support.validate.ValidatorHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @title GlobalExceptionAdvice.java
 * @description 全局配置异常处理，分为5种涵盖日常使用场景
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:15:40
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 运行
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public BaseResult runtimeException(HttpServletRequest request, Exception e) {
        log.error("runtimeException ......", e);
        return R.error(RunExc.RUNTIME.getErrorCode(), e.getMessage());
    }

    /**
     * 请求
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class})
    public BaseResult requestException(HttpServletRequest request, Exception e) {
        log.error("requestException ......", e);
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
        return R.error(RunExc.REQUEST.getErrorCode(), msg);
    }

    /**
     * 效验
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class, ValidationException.class})
    public BaseResult validatorException(HttpServletRequest request, Exception e) {
        log.error("validatorException ......", e);
        List<String> errorMsg = Lists.newArrayList();
        // 这里走的是Spring Violation 验证 --> Java Violation，这里有BindException接收
        if (e instanceof BindException) {
            BindingResult violations = ((BindException) e).getBindingResult();
            for (FieldError fieldError : violations.getFieldErrors()) {
                errorMsg.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
            }
        }
        // 这里走的是Hibernate Violation 验证 --> Java
        // Violation，这里有ConstraintViolationException接收
        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            errorMsg.addAll(ValidatorHelper.extractPropertyAndMessageAsList(violations));
        }
        return R.error(RunExc.VALI.getErrorCode(), RunExc.VALI.getMassage(), errorMsg);
    }

    /**
     * 数据库
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({DataAccessException.class})
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
