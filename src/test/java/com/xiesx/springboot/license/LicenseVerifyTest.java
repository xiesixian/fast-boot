package com.xiesx.springboot.license;

import com.xiesx.springboot.core.license.LicenseVerify;

public class LicenseVerifyTest {

    public static void main(String[] args) {

        LicenseVerify param = new LicenseVerify();
        // 证书主题
        param.setSubject("测试证书");
        // 公钥别称
        param.setPublicAlias("publicCert");
        // 访问公钥库的密码
        param.setStorePass("zedu@8888");
        // 公钥库存储路径
        param.setPublicKeysStorePath("E:\\key\\publicCerts.store");
        // 证书生成路径
        param.setLicensePath("E:\\key\\license");
        // 安装证书
        param.install();
        // 卸载证书
        // param.unInstall();
        // 校验证书
        System.out.println(param.verify());
    }
}
