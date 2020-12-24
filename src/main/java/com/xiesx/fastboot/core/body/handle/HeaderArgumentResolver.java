package com.xiesx.fastboot.core.body.handle;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.fastboot.core.body.annotation.GoHeader;
import com.xiesx.fastboot.core.body.cfg.HeaderCfg;
import com.xiesx.fastboot.core.body.handle.CurrentHeader.CurrentHeaderBuilder;

/**
 * @title HeaderArgumentResolver.java
 * @description
 * @author Sixian.xie
 * @date 2020-7-21 22:37:35
 */
public class HeaderArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentHeader.class) && parameter.hasParameterAnnotation(GoHeader.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
            WebDataBinderFactory factory) throws Exception {
        // 构造设备信息
        CurrentHeaderBuilder builder = CurrentHeader.builder();
        // 获取设备信息
        String device = request.getHeader(HeaderCfg.DEVICE);
        if (ObjectUtils.isNotEmpty(device)) {
            builder.device(device);
        }
        String osVersion = request.getHeader(HeaderCfg.OSVERSION);
        if (ObjectUtils.isNotEmpty(osVersion)) {
            builder.osVersion(osVersion);
        }
        String apVersion = request.getHeader(HeaderCfg.APVERSION);
        if (ObjectUtils.isNotEmpty(apVersion)) {
            builder.apVersion(apVersion);
        }
        String apVersionCode = request.getHeader(HeaderCfg.APVERSIONCODE);
        if (ObjectUtils.isNotEmpty(apVersionCode)) {
            builder.apVersionCode(apVersionCode);
        }
        String androidId = request.getHeader(HeaderCfg.ANDROIDID);
        if (ObjectUtils.isNotEmpty(androidId)) {
            builder.androidId(androidId);
        }
        String psuedoUniqueId = request.getHeader(HeaderCfg.PSUEDOUNIQUEID);
        if (ObjectUtils.isNotEmpty(psuedoUniqueId)) {
            builder.psuedoUniqueId(psuedoUniqueId);
        }
        String networkType = request.getHeader(HeaderCfg.NETWORKTYPE);
        if (ObjectUtils.isNotEmpty(networkType)) {
            builder.networkType(networkType);
        }
        return builder.build();
    }
}
