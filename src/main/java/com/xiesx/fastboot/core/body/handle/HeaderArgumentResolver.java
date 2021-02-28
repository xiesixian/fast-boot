package com.xiesx.fastboot.core.body.handle;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.fastboot.core.body.annotation.GoHeader;
import com.xiesx.fastboot.core.body.cfg.HeaderCfg;
import com.xiesx.fastboot.core.body.handle.CurrentHeader.CurrentHeaderBuilder;

import cn.hutool.core.util.StrUtil;

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
        if (StrUtil.isNotBlank(device)) {
            builder.device(device);
        }
        String osVersion = request.getHeader(HeaderCfg.OSVERSION);
        if (StrUtil.isNotBlank(osVersion)) {
            builder.osVersion(osVersion);
        }
        String apVersion = request.getHeader(HeaderCfg.APVERSION);
        if (StrUtil.isNotBlank(apVersion)) {
            builder.apVersion(apVersion);
        }
        String apVersionCode = request.getHeader(HeaderCfg.APVERSIONCODE);
        if (StrUtil.isNotBlank(apVersionCode)) {
            builder.apVersionCode(apVersionCode);
        }
        String androidId = request.getHeader(HeaderCfg.ANDROIDID);
        if (StrUtil.isNotBlank(androidId)) {
            builder.androidId(androidId);
        }
        String psuedoUniqueId = request.getHeader(HeaderCfg.PSUEDOUNIQUEID);
        if (StrUtil.isNotBlank(psuedoUniqueId)) {
            builder.psuedoUniqueId(psuedoUniqueId);
        }
        String networkType = request.getHeader(HeaderCfg.NETWORKTYPE);
        if (StrUtil.isNotBlank(networkType)) {
            builder.networkType(networkType);
        }
        return builder.build();
    }
}
