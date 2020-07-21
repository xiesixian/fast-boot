package com.xiesx.fastboot.support.license;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title LicenseCreatorParam.java
 * @description License生成类需要的参数
 * @author Sixian.xie
 * @date 2020-7-21 22:34:03
 */
@Data
public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 私钥库存储路径
     */
    private String privateKeysStorePath;

    /**
     * 访问私钥库的密码
     */
    private String storePass;

    /**
     * 私钥别称
     */
    private String privateAlias;

    /**
     * 私钥别称密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPass;

    /**
     * 证书生成路径
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
     * 消费类型
     */
    private String consumerType = "user";// user/free

    /**
     * 额外的服务器硬件校验信息
     */
    private LicenseCreatorParamExtra licenseExtraModel;
}
