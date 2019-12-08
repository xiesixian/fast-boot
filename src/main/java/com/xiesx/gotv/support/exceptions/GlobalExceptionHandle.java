package com.xiesx.gotv.support.exceptions;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

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
import com.xiesx.gotv.base.BaseResult;
import com.xiesx.gotv.support.validator.ValidatorHelper;

/**
 * @title GlobalExceptionHandle.java
 * @description 全局配置异常处理，分为3种涵盖日常使用场景
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:15:40
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

	/**
	 * 系统
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class })
	public BaseResult systemException(HttpServletRequest request, Exception e) {
		log.error("systemException ......", e);
		String msg = "";
		//
		if (e instanceof HttpMessageNotReadableException) {
			msg = "当前参数解析失败";// 400 - Bad Request
		} else if (e instanceof HttpRequestMethodNotSupportedException) {
			msg = "不支持当前请求方法";// 405 - Method Not Allowed
		} else if (e instanceof HttpMediaTypeNotSupportedException) {
			msg = "不支持当前媒体类型";// 415 - Unsupported Media Type
		} else {
			msg = "未知系统异常";
		}
		return BaseResult.error(msg);
	}

	/**
	 * 效验
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BindException.class, ConstraintViolationException.class })
	public BaseResult validatorException2(HttpServletRequest request, Exception e) {
		log.error("validatorException2 ......", e);
		BaseResult result = BaseResult.error("参数效验错误");
		// 这里走的是Spring Violation 验证 --> Java Violation，这里有BindException接收
		if (e instanceof BindException) {
			BindingResult violations = ((BindException) e).getBindingResult();
			List<String> errorMessages = new ArrayList<String>();
			for (FieldError fieldError : violations.getFieldErrors()) {
				errorMessages.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
			}
			result.setData(errorMessages);
		}
		// 这里走的是Hibernate Violation 验证 --> Java Violation，这里有ConstraintViolationException接收
		if (e instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
			List<String> errorMsg = ValidatorHelper.extractPropertyAndMessageAsList(violations);
			result.setData(errorMsg);
		}
		return result;
	}

	/**
	 * 运行
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class, NullPointerException.class, ClassCastException.class, IndexOutOfBoundsException.class, SocketException.class, IOException.class, JSONException.class, SQLException.class, ExecutionException.class,
			RetryException.class })
	public BaseResult runtimeException(HttpServletRequest request, Exception e) {
		//
		log.error("runtimeException ......", e);
		// 
		String msg = "";
		if (e instanceof NullPointerException) {
			msg = String.format("error code 9901 %s", e.getMessage());
		} else if (e instanceof ClassCastException) {
			msg = String.format("error code 9902 %s", e.getMessage());
		} else if (e instanceof IndexOutOfBoundsException) {
			msg = String.format("error code 9903 %s", e.getMessage());
		} else if (e instanceof SocketException) {
			msg = String.format("error code 9904 %s", e.getMessage());
		} else if (e instanceof IOException) {
			msg = String.format("error code 9905 %s", e.getMessage());
		} else if (e instanceof JSONException) {
			msg = String.format("error code 9906 %s", e.getMessage());
		} else if (e instanceof SQLException) {
			msg = String.format("error code 9907 %s", e.getMessage());
		} else if (e instanceof ExecutionException) {
			msg = String.format("error code 9908 %s", e.getMessage());
		} else if (e instanceof RetryException) {
			msg = String.format("error code 9909 %s", e.getMessage());
		}
		//
		log.error("runtimeException msg......", msg);
		return BaseResult.error(e.getMessage());
	}

	/**
	 * 自定义
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ ActException.class })
	public BaseResult actException(HttpServletRequest request, ActException e) {
		log.error("actException ......", e);
		String msg = String.format("error code %s %s", e.getErrorCode(), e.getMessage());
		return BaseResult.fail(msg);
	}
}
