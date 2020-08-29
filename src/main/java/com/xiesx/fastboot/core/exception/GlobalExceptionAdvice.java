package com.xiesx.fastboot.core.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.collect.Lists;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;
import com.xiesx.fastboot.support.validate.ValidatorHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @title GlobalExceptionAdvice.java
 * @description 全局配置异常处理，分为5种涵盖日常使用场景
 * @author Sixian.xie
 * @date 2020-7-21 22:30:43
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
        // 这里走的是Hibernate Violation 验证 --> Java Violation，这里有ConstraintViolationException接收
        if (e instanceof ConstraintViolationException) {
            errorMsg.addAll(ValidatorHelper.extractPropertyAndMessageAsList(((ConstraintViolationException) e)));
        }
        return R.error(RunExc.VALI.getErrorCode(), RunExc.VALI.getErrorMsg(), errorMsg);
    }

    /**
     * 数据库
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
    public BaseResult jdbcException(HttpServletRequest request, Exception e) {
        log.error("jdbcException ......", e);
        String msg = "";
        if (e instanceof EmptyResultDataAccessException) {
            msg = "无数据";
        } else if (e instanceof InvalidDataAccessApiUsageException) {
            msg = "无数据";
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
