package com.xiesx.fastboot.support.license;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
public class LicenseHelperTest {

    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 主题
    public static String subject = "fast-boot";

    // 密钥库密码
    public static String storepass = "136305973@qq.com";

    // 私钥密码
    public static String privatepass = "xiesx@888";

    @Test
    public void creator() {
        // 生效时间
        Date issusedDate = new Date();
        log.info("license generate issusedDate {}", format.format(issusedDate));
        // 过期时间
        Date expiryDate = DateUtils.addDays(issusedDate, 30);
        log.info("license generate expiryDate {}", format.format(expiryDate));
        // 额外信息
        LicenseParamsExtra licenseExtraModel = new LicenseParamsExtra();
        // 构造参数
        LicenseParams params = new LicenseParams()
                // 证书主题
                .setSubject(subject)
                // 密钥库密码
                .setStorePass(storepass)
                // 私钥路径
                .setPrivateKeysStorePath(LicenseHelperTest.class.getResource("privateKeys.store").getPath())
                // 私钥别称
                .setPrivateAlias("privateKey")
                // 私钥密码
                .setPrivatePass(privatepass)
                // 证书生成路径
                .setLicensePath(LicenseHelperTest.class.getResource("license.lic").getPath())
                // 证书生效时间
                .setIssuedTime(issusedDate)
                // 证书失效时间
                .setExpiryTime(expiryDate)
                // 额外信息
                .setLicenseExtraModel(licenseExtraModel);
        // 生成证书
        LicenseCreator licenseCreator = new LicenseCreator(params);
        log.info("license generate {}", licenseCreator.generateLicense() ? "success" : "fail");
    }

    @Test
    public void verify() throws Exception {
        // 证书信息
        LicenseVerify param = new LicenseVerify()
                // 证书主题
                .setSubject(subject)
                // 公钥路径
                .setPublicKeysStorePath("E:/license/zedu/publicCerts.store")
                // 公钥别称
                .setPublicAlias("publicCert")
                // 密钥库密码
                .setStorePass(storepass)
                // 证书路径
                .setLicensePath("E:/license/zedu/license.lic");
        // 安装证书
        param.install();
        // 校验证书
        param.verify();
    }
}
