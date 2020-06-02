package com.xiesx.fastboot.core.token.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = TokenProperties.PREFIX)
public class TokenProperties {

    public static final String PREFIX = "fastboot.token";

    private String headerKey;

    private String paths;

    private String excludePaths;
}
