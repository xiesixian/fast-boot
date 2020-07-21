package com.xiesx.fastboot.core.logger;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.xiesx.fastboot.core.logger.annotation.GoLog;

import lombok.extern.slf4j.Slf4j;

/**
 * @title LoggerAspect.java
 * @description 日志打印切面
 * @author Sixian.xie
 * @date 2020-7-21 22:35:02
 */
@Slf4j
@Component
@Aspect
@Order(-100)
public class LoggerAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logPointcut() {
        log.debug("logPointcut=====");
    }

    @Around("logPointcut()")
    public Object logAroundAspect(ProceedingJoinPoint pjp) throws Throwable {
        //// 获取到当前线程绑定的请求对象
        // ServletRequestAttributes servlet = (ServletRequestAttributes)
        //// RequestContextHolder.getRequestAttributes();
        // 获取Request
        // HttpServletRequest request = servlet.getRequest();
        // String ip = getIpAddr(request);
        // String type = request.getMethod();
        // String url = request.getRequestURI();
        //// 获取Session
        // HttpSession session = request.getSession();
        // 获取方法信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        // 获取注解信息
        GoLog annotation = method.getAnnotation(GoLog.class);
        Boolean isPrint = Boolean.valueOf(annotation == null ? false : annotation.print());
        Boolean isPrettyFormat = Boolean.valueOf(annotation == null ? false : annotation.format());
        // 获取入参
        Object[] args = pjp.getArgs();
        // 请求
        String req = "";
        if (args.length != 0) {
            for (Object arg : args) {
                if ((!(arg instanceof ServletRequest)) && (!(arg instanceof ServletResponse)) && (!(arg instanceof MultipartFile))
                        && (!(arg instanceof Model))) {
                    req = JSON.toJSONString(arg, isPrettyFormat);
                }
            }
        }
        // 记录开始时间
        long beginTime = System.currentTimeMillis();
        if (isPrint) {
            log.info("| request {} {} {}ms", new Object[] {methodName, Long.valueOf(beginTime), req});
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
            log.info("| response {} {} {} {}ms", new Object[] {methodName, Long.valueOf(endTime), res, Long.valueOf(t)});
        }
        return ret;
    }
    // private String getIpAddr(HttpServletRequest request) {
    // String ip = request.getHeader("X-real-ip");
    // if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    // ip = request.getHeader("X-Forwarded-For");
    // }
    // if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    // ip = request.getHeader("Proxy-Client-IP");
    // }
    // if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    // ip = request.getHeader("WL-Proxy-Client-IP");
    // }
    // if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
    // ip = request.getRemoteAddr();
    // }
    // return ip;
    // }
}
