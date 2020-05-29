package com.xiesx.springboot.support.token.handle;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.springboot.support.token.annotation.Token;
import com.xiesx.springboot.support.token.cfg.TokenCfg;
import com.xiesx.springboot.support.token.handle.CurrentToken.CurrentTokenBuilder;

/**
 * @title TokenMethodArgumentResolverHandler
 * @description
 * @author XIE
 * @date 2020年4月25日下午6:15:53
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentToken.class) && parameter.hasParameterAnnotation(Token.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
            WebDataBinderFactory factory) throws Exception {
        // 构造用户信息
        CurrentTokenBuilder builder = CurrentToken.builder();
        // 获取用户id
        String user_id = request.getAttribute(TokenCfg.USERID, RequestAttributes.SCOPE_REQUEST).toString();
        if (ObjectUtils.isEmpty(user_id)) {
            return null;
        }
        String user_name = request.getAttribute(TokenCfg.USERNAME, RequestAttributes.SCOPE_REQUEST).toString();
        if (ObjectUtils.isNotEmpty(user_name)) {
            builder.userName(user_name);
        }
        String nick_name = request.getAttribute(TokenCfg.NICKNAME, RequestAttributes.SCOPE_REQUEST).toString();
        if (ObjectUtils.isNotEmpty(nick_name)) {
            builder.nickName(nick_name);
        }
        return builder.userId(user_id).build();
    }
}
