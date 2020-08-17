package com.xiesx.fastboot.support.license;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import de.schlichtherle.license.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title LicenseVerify.java
 * @description License校验类
 * @author Sixian.xie
 * @date 2020-7-21 22:34:31
 */
@Slf4j
@Data
public class LicenseVerify {

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥库存储路径
     */
    private String publicKeysStorePath;

    /**
     * 访问私钥库的密码
     */
    private String storePass;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * LicenseManager
     */
    private LicenseManager licenseManager;

    /**
     * 标识证书是否安装成功
     */
    private boolean installSuccess;

    public LicenseVerify() {}

    public LicenseVerify(String subject, String publicAlias, String storePass, String licensePath, String publicKeysStorePath) {
        this.subject = subject;
        this.publicKeysStorePath = publicKeysStorePath;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licensePath = licensePath;
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        // 绑定创建class路径
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);
        // 密钥存储
        KeyStoreParam privateStoreParam = new LicenseKeyStoreParam(LicenseCreator.class, publicKeysStorePath, publicAlias, storePass, null);
        // 加密秘钥
        CipherParam cipherParam = new DefaultCipherParam(storePass);
        return new DefaultLicenseParam(subject, preferences, privateStoreParam, cipherParam);
    }

    /**
     * 安装证书
     *
     * @throws Exception
     */
    public void install() throws Exception {
        licenseManager = new LicenseManagerLocal(initLicenseParam());
        licenseManager.uninstall();
        licenseManager.install(new File(licensePath));
        installSuccess = true;
        log.info("Startup License Install Success");
    }

    /**
     * 卸载证书
     *
     * @throws Exception
     */
    public void unInstall() throws Exception {
        if (installSuccess) {
            licenseManager.uninstall();
        }
    }

    /**
     * 校验证书
     *
     * @throws Exception
     */
    public boolean verify() throws Exception {
        if (installSuccess) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LicenseContent licenseContent = licenseManager.verify();
            log.info("Startup License Verify Success {} - {}", format.format(licenseContent.getNotBefore()),
                    format.format(licenseContent.getNotAfter()));
            return true;
        } else {
            return false;
        }
    }
}
