package com.xiesx.springboot.support.sgin;

import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.xiesx.springboot.core.exception.RunExc;
import com.xiesx.springboot.core.exception.RunException;
import com.xiesx.springboot.support.sgin.annotation.GoSign;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SignAspect.java
 * @description 数据签名
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:14:55
 */
@Slf4j
@Component
@Aspect
@Order(2)
public class SignAspect {

    public static final String SIGN_KEY = "sign";

    public static final String SIGN_VAL = "1234567890";

    @Value("${fb.sign}")
    String sign;

    @Value("${fb.active}")
    String active;

    @Pointcut("@annotation(com.xiesx.gotv.core.sgin.annotation.GoSign)")
    public void signPointcut() {
        log.debug("signPointcut=====");
    }

    @Around("signPointcut()")
    public Object signBeforeAspect(ProceedingJoinPoint pjp) throws RunException, Throwable {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // 获取注解信息
        GoSign annotation = method.getAnnotation(GoSign.class);
        Boolean isIgnore = Boolean.valueOf(annotation == null ? true : annotation.ignore());
        // 参数集
        Map<String, String> parms = Maps.newConcurrentMap();
        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取参数
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            parms.put(name, value);
        }
        // 判断是否使用默认配置
        if (StringUtils.isEmpty(sign)) {
            sign = SIGN_VAL;
        }
        // 是否进行效验
        if (active.equals("release") && !isIgnore) {
            if (!parms.isEmpty()) {
                // 从header中获取sign
                String sign = request.getHeader(SIGN_KEY);
                // sign为空
                if (StringUtils.isEmpty(sign)) {
                    throw new RunException(RunExc.SIGNA, "非法请求");
                } else if (!getSignature(parms, sign).equals(sign)) {
                    throw new RunException(RunExc.SIGNA, "验签失败");
                } else {
                    return pjp.proceed();
                }
            }
        }
        return null;
    }

    /**
     * 获取签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String getSignature(Map<String, String> params, String key) {
        return DigestUtils.md5Hex(getSortParams(params) + "&key=" + key);
    }

    /**
     * 按key进行正序排列，之间以&相连 <功能描述>
     *
     * @param params
     * @return
     */
    public static String getSortParams(Map<String, String> params) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        for (String key : params.keySet()) {
            map.put(key, params.get(key));
        }
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        String str = "";
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            str += key + "=" + value + "&";
        }
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("qq", SIGN_VAL);
        System.out.println(getSignature(params, SIGN_VAL));

        params = new HashMap<>();
        params.put("url", "https://www.iqiyi.com/v_19rsmej7tw.html?vfm=2008_aldbd");
        System.out.println(getSignature(params, SIGN_VAL));

        params = new HashMap<>();
        params.put("url", "https://v.youku.com/v_show/id_XNDE0ODQ5NzczNg==.html");
        System.out.println(getSignature(params, SIGN_VAL));
    }
}
