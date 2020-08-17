package com.xiesx.fastboot.support.license.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = LicenseProperties.PREFIX)
public class LicenseProperties {

    public static final String PREFIX = "fastboot.license";

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥库存储路径
     */
    private String publicStorePath;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 密钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;
}
