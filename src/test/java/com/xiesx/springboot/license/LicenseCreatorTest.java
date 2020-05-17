package com.xiesx.springboot.license;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.google.common.collect.Lists;
import com.xiesx.springboot.core.license.LicenseCreator;
import com.xiesx.springboot.core.license.LicenseCreatorParam;
import com.xiesx.springboot.core.license.LicenseExtraModel;

public class LicenseCreatorTest {

    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws ParseException {

        LicenseCreatorParam param = new LicenseCreatorParam();
        // 证书主题
        param.setSubject("测试证书");
        // 私钥别称
        param.setPrivateAlias("privateKey");
        // 私钥密码
        param.setKeyPass("zedu@888");
        // 访问私钥库的密码
        param.setStorePass("zedu@8888");
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
        Date expiryDate = DateUtils.addMinutes(issusedDate, 1);
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
        LicenseCreator licenseCreator = new LicenseCreator(param);
        // 生成license
        System.out.println(licenseCreator.generateLicense());
    }
}
