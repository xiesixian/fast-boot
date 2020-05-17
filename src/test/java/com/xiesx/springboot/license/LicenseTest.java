package com.xiesx.springboot.license;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.xiesx.springboot.core.license.LicenseCreator;
import com.xiesx.springboot.core.license.LicenseCreatorParam;
import com.xiesx.springboot.core.license.LicenseExtraModel;
import com.xiesx.springboot.core.license.LicenseVerify;

@RunWith(SpringJUnit4ClassRunner.class)
public class LicenseTest {

    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String subject = "FAST";

    public static String storepass = "136305973@qq.com";

    public static String keypass = "xiesx@888";

    @Test
    public void create() {

        LicenseCreatorParam param = new LicenseCreatorParam();
        // 证书主题
        param.setSubject(subject);
        // 私钥别称
        param.setPrivateAlias("privateKey");
        // 访问私钥库的密码
        param.setStorePass(storepass);
        // 私钥密码
        param.setKeyPass(keypass);
        // 私钥库存储路径
        param.setPrivateKeysStorePath("E:\\key\\privateKeys.store");
        // 证书生成路径
        param.setLicensePath("E:\\key\\license");
        // 证书生效时间
        // Date issusedDate = DateUtils.parseDate("2019-12-31 23:59:59", "yyyy-MM-dd
        // HH:mm:ss");
        Date issusedDate = new Date();
        System.out.println(format.format(issusedDate));
        param.setIssuedTime(issusedDate);
        // 证书失效时间
        Date expiryDate = DateUtils.addMinutes(issusedDate, 30);
        System.out.println(format.format(expiryDate));
        param.setExpiryTime(expiryDate);
        // 消费类型
        param.setConsumerType("user");
        // 额外信息
        List<String> macs = Lists.newArrayList();
        macs.add("00:01:6C:06:A6:29");
        LicenseExtraModel licenseExtraModel = new LicenseExtraModel();
        licenseExtraModel.setMacAddress(macs);
        param.setLicenseExtraModel(licenseExtraModel);
        // 生成license
        LicenseCreator licenseCreator = new LicenseCreator(param);
        System.out.println(licenseCreator.generateLicense());
    }

    @Test
    public void verify() {
        //
        LicenseVerify param = new LicenseVerify();
        // 证书主题
        param.setSubject(subject);
        // 公钥别称
        param.setPublicAlias("publicCert");
        // 访问公钥库的密码
        param.setStorePass(storepass);
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
