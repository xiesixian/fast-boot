package com.xiesx.fastboot.core.license;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import de.schlichtherle.license.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * License校验类
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
     */
    public void install() {
        try {
            licenseManager = new LicenseManagerLocal(initLicenseParam());
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(new File(licensePath));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            installSuccess = true;
            log.debug("------------------------------- 证书安装成功 -------------------------------");
            log.debug(MessageFormat.format("有效期：{0} - {1}", format.format(licenseContent.getNotBefore()),
                    format.format(licenseContent.getNotAfter())));
        } catch (Exception e) {
            installSuccess = false;
            e.printStackTrace();
            log.error("------------------------------- 证书安装失败 -------------------------------");
        }
    }

    /**
     * 卸载证书
     */
    public void unInstall() {
        if (installSuccess) {
            try {
                licenseManager.uninstall();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("------------------------------- 证书卸载失败 -------------------------------");
            }
        }
    }

    /**
     * 校验证书
     */
    public boolean verify() {
        try {
            if (installSuccess) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                LicenseContent licenseContent = licenseManager.verify();
                log.debug("------------------------------- 证书校验通过 -------------------------------");
                log.debug(MessageFormat.format("有效期：{0} - {1}", format.format(licenseContent.getNotBefore()),
                        format.format(licenseContent.getNotAfter())));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------- 证书校验失败 -------------------------------");
            return false;
        }
    }
}