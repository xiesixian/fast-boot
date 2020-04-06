package com.xiesx.gotv.core.logger;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.xiesx.gotv.core.logger.annotation.LoggerStorage;

import lombok.extern.slf4j.Slf4j;

/**
 * @title LoggerAspect.java
 * @description 日志输出
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:14:55
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class LoggerAspect {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void logPointcut() {
		log.info("logPointcut=====");
	}

	@Around("logPointcut()")
	public Object logAroundAspect(ProceedingJoinPoint pjp) throws Throwable {
		// 获取请求信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = getIpAddr(request);
		String type = request.getMethod();
		String url = request.getRequestURI();
		// 获取方法信息
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
		// 获取注解信息
		LoggerStorage annotation = (LoggerStorage) method.getAnnotation(LoggerStorage.class);
		Boolean isPrint = Boolean.valueOf(annotation == null ? true : annotation.print());
		Boolean isStorage = Boolean.valueOf(annotation == null ? false : annotation.storage());
		Boolean isPrettyFormat = Boolean.valueOf(annotation == null ? false : annotation.prettyFormat());
		// 获取入参
		Object[] args = pjp.getArgs();
		// 请求
		String req = "unknown";
		if (args.length != 0) {
			for (Object arg : args) {
				if ((!(arg instanceof ServletRequest)) && (!(arg instanceof ServletResponse))
						&& (!(arg instanceof MultipartFile)) && (!(arg instanceof Model))) {
					req = JSON.toJSONString(arg, isPrettyFormat);
				}
			}
		}
		// 记录开始时间
		long beginTime = System.currentTimeMillis();
		if (isPrint) {
			log.info("=========request sta {} {} {}", new Object[] { methodName, Long.valueOf(beginTime), req });
		}
		// 执行方法
		Object ret = pjp.proceed();
		// 记录结束时间
		long endTime = System.currentTimeMillis();
		// 执行时间
		long t = System.currentTimeMillis() - beginTime;
		// 响应
		String res = JSON.toJSONString(ret, isPrettyFormat);
		if (isPrint) {
			log.info("=========request end {} {} {} {}",
					new Object[] { methodName, Long.valueOf(endTime), res, Long.valueOf(t) });
		}
		if (isStorage) {
			try {
				LogStorage log = new LogStorage();
				log.setId(IdWorker.getIdStr(log));
				log.setCreateDate(new Date());
				log.setIp(ip);
				log.setMethod(methodName);
				log.setType(type);
				log.setUrl(url);
				log.setReq(req);
				log.setRes(res);
				log.setT(t);
				log.insert();
			} catch (Exception e) {
				log.error("=========request err {}", e);
			}
		}
		return ret;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}