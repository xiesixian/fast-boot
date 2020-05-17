package com.xiesx.springboot.core.license;

import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.schlichtherle.license.*;

/**
 * License生成类 -- 用于license生成
 */
public class LicenseCreator {

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER =
            new X500Principal("CN=FAST, OU=JavaSoft, O=XSX, L=WUHAN, ST=HUBEI, C=CN");

    private static Logger logger = LogManager.getLogger(LicenseCreator.class);

    private LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * 生成License证书
     */
    public boolean generateLicense() {
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();
            licenseManager.store(licenseContent, new File(param.getLicensePath()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(MessageFormat.format("证书生成失败：{0}", param), e);
            return false;
        }
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
        licenseContent.setConsumerType(param.getConsumerType());

        // 扩展校验，这里可以自定义一些额外的校验信息(也可以用json字符串保存)
        if (param.getLicenseExtraModel() != null) {
            licenseContent.setExtra(param.getLicenseExtraModel());
        }
        return licenseContent;
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        // 设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class, param.getPrivateKeysStorePath(),
                param.getPrivateAlias(), param.getStorePass(), param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject(), preferences, privateStoreParam, cipherParam);
    }

}
