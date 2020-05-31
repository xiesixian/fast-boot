package com.xiesx.springboot.core.sign.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = SignProperties.PREFIX)
public class SignProperties {

    public static final String PREFIX = "fastboot.sign";

    private String key;

    private String val;
}
