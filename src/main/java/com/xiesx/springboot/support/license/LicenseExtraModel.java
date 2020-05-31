package com.xiesx.springboot.support.license;

import java.util.List;

import lombok.Data;

/**
 * 自定义需要校验的License参数，可以增加一些额外需要校验的参数，比如项目信息，ip地址信息等等，待完善
 */
@Data
public class LicenseExtraModel {

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;
}
