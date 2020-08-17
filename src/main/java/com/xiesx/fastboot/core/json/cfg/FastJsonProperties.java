package com.xiesx.fastboot.core.json.cfg;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = FastJsonProperties.PREFIX)
public class FastJsonProperties {

    public static final String PREFIX = "fastboot.fastjson";

    private String dateFormat;

    private Boolean desensitize;
}
