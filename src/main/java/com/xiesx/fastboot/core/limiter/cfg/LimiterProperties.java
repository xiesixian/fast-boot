package com.xiesx.fastboot.core.limiter.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = LimiterProperties.PREFIX)
public class LimiterProperties {

    public static final String PREFIX = "fastboot.limit";

    private Integer timeout;
}
