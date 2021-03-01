package com.xiesx.fastboot.support.license;

import java.io.File;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @title LicenseCreator.java
 * @description License生成类 -- 用于license生成
 * @author Sixian.xie
 * @date 2020-7-21 22:33:56
 */
@Log4j2
@RequiredArgsConstructor
public class LicenseCreator {

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER =
            new X500Principal("CN=FAST, OU=JavaSoft, O=XSX, L=WUHAN, ST=HUBEI, C=CN");

    @NonNull
    private LicenseParams param;

    /**
     * 生成License证书
     */
    public boolean generateLicense() {
        try {
            LicenseManager licenseManager = new LicenseManagerLocal(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();
            licenseManager.store(licenseContent, new File(param.getLicensePath()));
            return true;
        } catch (Exception e) {
            log.error("证书生成失败", e);
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);
        // 设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam privateStoreParam = new LicenseKeyStoreParam(LicenseCreator.class, param.getPrivateKeysStorePath(),
                param.getPrivateAlias(), param.getStorePass(), param.getPrivatePass());
        return new DefaultLicenseParam(param.getSubject(), preferences, privateStoreParam, cipherParam);
    }

    /**
     * 设置证书生成正文信息
     */
    private LicenseContent initLicenseContent() {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType("user");

        // 扩展校验，这里可以自定义一些额外的校验信息(也可以用json字符串保存)
        if (param.getLicenseExtraModel() != null) {
            licenseContent.setExtra(param.getLicenseExtraModel());
        }
        return licenseContent;
    }
}
