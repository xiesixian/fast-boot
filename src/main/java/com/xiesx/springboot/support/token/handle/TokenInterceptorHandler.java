package com.xiesx.springboot.support.token.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiesx.springboot.core.exception.RunExc;
import com.xiesx.springboot.core.exception.RunException;
import com.xiesx.springboot.support.token.JwtHelper;
import com.xiesx.springboot.support.token.annotation.Token;
import com.xiesx.springboot.support.token.cfg.TokenCfg;
import com.xiesx.springboot.support.token.cfg.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * @title TokenInterceptorHandler
 * @description
 * @author XIE
 * @date 2020年4月25日下午6:17:04
 */
@Slf4j
public class TokenInterceptorHandler extends HandlerInterceptorAdapter {

    private final TokenProperties properties;

    public static final String TOKEN_KEY = "token";

    String key;

    public TokenInterceptorHandler(TokenProperties mTokenProperties) {
        this.properties = mTokenProperties;
        key = StringUtils.isNotEmpty(properties.getKey()) ? properties.getKey() : TOKEN_KEY;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取方法信息
        Method method = ((HandlerMethod) handler).getMethod();
        // 获取参数注解信息
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotation1 : parameterAnnotations) {
            for (Annotation annotation2 : annotation1) {
                if (annotation2 instanceof Token) {
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
                            throw new RunException(RunExc.TOKEN, "已失效");
                        } else {
                            throw new RunException(RunExc.TOKEN);
                        }
                    }
                }
            }
        }
        return true;
    }
}
