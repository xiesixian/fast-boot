package com.xiesx.fastboot.core.sign;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;
import com.xiesx.fastboot.core.sign.annotation.GoSignal;
import com.xiesx.fastboot.core.sign.cfg.SignalProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SignalAspect.java
 * @description 数据签名切面
 * @author Sixian.xie
 * @date 2020-7-21 22:35:39
 */
@Slf4j
@Component
@Aspect
@Order(-98)
public class SignalAspect {

    public static final String SIGN_KEY = "sign";

    public static final String SIGN_VAL = "123456780";

    @Autowired
    private SignalProperties properties;

    private String key;

    private String secret;

    @Pointcut("@annotation(com.xiesx.fastboot.core.sign.annotation.GoSignal)")
    public void signPointcut() {
        log.debug("signPointcut=====");
    }

    @Around("signPointcut()")
    public Object signBeforeAspect(ProceedingJoinPoint pjp) throws RunException, Throwable {
        // 获取配置
        key = StringUtils.isNotEmpty(properties.getHeader()) ? properties.getHeader() : SIGN_KEY;
        secret = StringUtils.isNotEmpty(properties.getSecret()) ? properties.getSecret() : SIGN_VAL;
        // 获取方法信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // 获取注解信息
        GoSignal annotation = method.getAnnotation(GoSignal.class);
        Boolean isIgnore = annotation == null ? false : !annotation.ignore();
        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取参数
        Map<String, String> parms = Maps.newConcurrentMap();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            parms.put(name, value);
        }
        // 是否进行效验
        if (isIgnore && !parms.isEmpty()) {
            // 从header中获取sign
            String headerSign = request.getHeader(key);
            // sign为空
            if (StringUtils.isEmpty(headerSign)) {
                throw new RunException(RunExc.SIGNA, "非法请求");
            }
            // sign错误
            if (!getSignature(parms, secret).equals(headerSign)) {
                throw new RunException(RunExc.SIGNA, "验签失败");
            }
        }
        return pjp.proceed();
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
        params.put("a", "1");
        params.put("b", "2");
        System.out.println(getSignature(params, SIGN_VAL));

        params = new HashMap<>();
        params.put("url", "https://www.iqiyi.com/v_19rsmej7tw.html?vfm=2008_aldbd");
        System.out.println(getSignature(params, SIGN_VAL));

        params = new HashMap<>();
        params.put("url", "https://v.youku.com/v_show/id_XNDE0ODQ5NzczNg==.html");
        System.out.println(getSignature(params, SIGN_VAL));
    }
}
