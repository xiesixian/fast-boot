package com.xiesx.fastboot.support.license;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @title LicenseCreatorParam.java
 * @description License生成类需要的参数
 * @author Sixian.xie
 * @date 2020-7-21 22:34:03
 */
@SuppressWarnings("all")
@Data
@Accessors(chain = true)
public class LicenseParams implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 证书主题
     */
    private String subject;

    /**
     * 密钥库密码
     */
    private String storePass;

    /**
     * 私钥路径
     */
    private String privateKeysStorePath;

    /**
     * 私钥别称
     */
    private String privateAlias;

    /**
     * 私钥密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPass;

    /**
     * 证书路径
     */
    private String licensePath;

    /**
     * 证书生效时间
     */
    private Date issuedTime;

    /**
     * 证书失效时间
     */
    private Date expiryTime;

    /**
     * 额外信息
     */
    private LicenseParamsExtra licenseExtraModel;
}
