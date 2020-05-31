package com.xiesx.springboot.support.license;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import de.schlichtherle.xml.XMLConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义LicenseManager，用于增加额外的信息校验(除了LicenseManager的校验，我们还可以在这个类里面添加额外的校验信息)
 */
@Slf4j
public class GoLicenseManager extends LicenseManager {

    public GoLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内
     *
     * 如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     *
     * @author zifangsky
     * @date 2018/4/24 11:44
     * @since 1.0.0
     * @return boolean
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList) {
        if (expectedList != null && expectedList.size() > 0) {
            if (serverList != null && serverList.size() > 0) {
                for (String expected : expectedList) {
                    if (serverList.contains(expected.trim())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 复写create方法
     */
    @Override
    protected synchronized byte[] create(LicenseContent content, LicenseNotary notary) throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * 复写install方法，其中validate方法调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     */
    @Override
    protected synchronized LicenseContent install(final byte[] key, final LicenseNotary notary) throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);
        return content;
    }

    /**
     * 重写XMLDecoder解析XML
     */
    private Object load(String encoded) {
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XMLConstants.XML_CHARSET)));
            decoder = new XMLDecoder(new BufferedInputStream(inputStream, XMLConstants.DEFAULT_BUFSIZE), null, null);
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (decoder != null) {
                    decoder.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("XMLDecoder解析XML失败", e);
            }
        }

        return null;
    }

    /**
     * 复写validate方法，用于增加我们额外的校验信息
     */
    @Override
    protected synchronized void validate(final LicenseContent content) throws LicenseContentException {
        // 1. 首先调用父类的validate方法
        super.validate(content);
        // 2. 然后校验自定义的License参数，去校验我们的license信息
        LicenseExtraModel expectedCheckModel = (LicenseExtraModel) content.getExtra();
        // 3. 自定义校验Mac地址
        if (!checkIpAddress(expectedCheckModel.getMacAddress(), expectedCheckModel.getMacAddress())) {
            throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
        }
    }

    /**
     * 校验生成证书的参数信息
     */
    protected synchronized void validateCreate(final LicenseContent content) throws LicenseContentException {
        // final LicenseParam param = getLicenseParam();
        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)) {
            throw new LicenseContentException("证书失效时间不能早于当前时间");
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)) {
            throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType) {
            throw new LicenseContentException("用户类型不能为空");
        }
    }

    /**
     * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary) throws Exception {
        final byte[] key = getLicenseKey();
        if (null == key) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }
        GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);
        return content;
    }
}
