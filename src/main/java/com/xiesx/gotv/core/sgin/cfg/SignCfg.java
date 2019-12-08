package com.xiesx.gotv.core.sgin.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.sgin.SignAspect;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Configuration
@Import({ SignAspect.class })
public class SignCfg {

}