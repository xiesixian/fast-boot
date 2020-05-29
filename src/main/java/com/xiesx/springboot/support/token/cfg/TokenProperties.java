package com.xiesx.springboot.support.token.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = TokenProperties.PREFIX)
public class TokenProperties {

    public static final String PREFIX = "fastboot.token";

    private String key;

    private String paths;

    private String excludePaths;
}
