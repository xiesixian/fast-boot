package com.xiesx.fastboot.core.token.handle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @title CurrentToken
 * @description
 * @author XIE
 * @date 2020年4月25日下午6:15:48
 */
@Data
@Builder
@AllArgsConstructor
public class CurrentToken {

    private String userId;

    private String userName;

    private String nickName;
}
