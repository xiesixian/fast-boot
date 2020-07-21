package com.xiesx.fastboot.core.token.handle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @title CurrentToken.java
 * @description 当前token附带信息
 * @author Sixian.xie
 * @date 2020-7-21 22:37:08
 */
@Data
@Builder
@AllArgsConstructor
public class CurrentToken {

    private String userId;

    private String userName;

    private String nickName;
}
