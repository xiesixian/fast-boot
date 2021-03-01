package com.xiesx.fastboot.support.license;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import de.schlichtherle.license.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

/**
 * @title LicenseVerify.java
 * @description License校验类
 * @author Sixian.xie
 * @date 2020-7-21 22:34:31
 */
@Log4j2
@Data
@Accessors(chain = true)
public class LicenseVerify {

    /**
     * 证书主题
     */
    private String subject;

    /**
     * 公钥路径
     */
    private String publicKeysStorePath;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 证书路径
     */
    private String licensePath;

    /**
     * 访问私钥库的密码
     */
    private String storePass;

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
        // 绑定创建Class路径
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);
        // 密钥信息
        KeyStoreParam privateStoreParam = new LicenseKeyStoreParam(LicenseCreator.class, publicKeysStorePath, publicAlias, storePass);
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
        log.info("license install success");
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
            log.info("license verify success {} - {}", format.format(licenseContent.getNotBefore()),
                    format.format(licenseContent.getNotAfter()));
            return true;
        } else {
            return false;
        }
    }
}
