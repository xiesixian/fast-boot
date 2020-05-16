package com.xiesx.springboot.support.token.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiesx.springboot.support.token.JwtHelper;
import com.xiesx.springboot.support.token.annotation.Token;
import com.xiesx.springboot.support.token.cfg.TokenCfg;

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
                    String token = request.getHeader("token");
                    if (StringUtils.isEmpty(token)) {
                        throw new RuntimeException("未登录");
                    }
                    try {
                        // 获取token
                        Claims claims = JwtHelper.parser(token);
                        // 设置requeest
                        request.setAttribute(TokenCfg.USER_KEY, claims.get("user_id", String.class));
                        request.setAttribute(TokenCfg.USER_NAME, claims.get("user_name", String.class));
                        request.setAttribute(TokenCfg.NICK_NAME, claims.get("nick_name", String.class));
                    } catch (Exception e) {
                        log.error("jwt token error", e);
                        if (e instanceof ExpiredJwtException) {
                            throw new RuntimeException("登录已失效");
                        } else {
                            throw new RuntimeException("登录错误");
                        }
                    }
                }
            }
        }
        return true;
    }
}
