package com.xiesx.fastboot.core.token.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xiesx.fastboot.SpringHelper;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;
import com.xiesx.fastboot.core.token.JwtHelper;
import com.xiesx.fastboot.core.token.annotation.GoToken;
import com.xiesx.fastboot.core.token.cfg.TokenCfg;
import com.xiesx.fastboot.core.token.cfg.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * @title TokenInterceptorHandler.java
 * @description
 * @author Sixian.xie
 * @date 2020-7-21 22:37:38
 */
@Slf4j
public class TokenInterceptorHandler implements HandlerInterceptor {

    private static final String TOKEN_KEY = "token";

    private String key;

    /**
     * Controller执行之前，如果返回false，controller不执行
     *
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle ......");
        // 获取配置
        TokenProperties properties = SpringHelper.getBean(TokenProperties.class);
        key = StringUtils.isNotEmpty(properties.getHeader()) ? properties.getHeader() : TOKEN_KEY;
        // 获取方法信息
        if (handler instanceof HandlerMethod) {
            // 获取方法
            Method method = ((HandlerMethod) handler).getMethod();
            // 获取参数注解信息
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] annotation1 : parameterAnnotations) {
                for (Annotation annotation2 : annotation1) {
                    if (annotation2 instanceof GoToken) {
                        // 获取token
                        String token = request.getHeader(key);
                        if (StringUtils.isEmpty(token)) {
                            throw new RunException(RunExc.TOKEN, "未登录");
                        }
                        try {
                            // 获取token
                            Claims claims = JwtHelper.parser(token);
                            // 设置requeest
                            request.setAttribute(TokenCfg.USERID, claims.getOrDefault(TokenCfg.USERID, ""));
                            request.setAttribute(TokenCfg.USERNAME, claims.getOrDefault(TokenCfg.USERNAME, ""));
                            request.setAttribute(TokenCfg.NICKNAME, claims.getOrDefault(TokenCfg.NICKNAME, ""));
                        } catch (Exception e) {
                            log.error("jwt token error", e);
                            if (e instanceof ExpiredJwtException) {
                                throw new RunException(RunExc.TOKEN, "失效");
                            } else {
                                throw new RunException(RunExc.TOKEN);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Controller执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        log.debug("postHandle ......");
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion ......");
    }
}
