package com.xiesx.springboot.core.token.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiesx.springboot.SpringHelper;
import com.xiesx.springboot.core.exception.RunExc;
import com.xiesx.springboot.core.exception.RunException;
import com.xiesx.springboot.core.token.JwtHelper;
import com.xiesx.springboot.core.token.annotation.GoToken;
import com.xiesx.springboot.core.token.cfg.TokenCfg;
import com.xiesx.springboot.core.token.cfg.TokenProperties;

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
        key = StringUtils.isNotEmpty(properties.getKey()) ? properties.getKey() : TOKEN_KEY;
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
                                throw new RunException(RunExc.TOKEN, "已失效");
                            } else {
                                throw new RunException(RunExc.TOKEN);
                            }
                        }
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
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
