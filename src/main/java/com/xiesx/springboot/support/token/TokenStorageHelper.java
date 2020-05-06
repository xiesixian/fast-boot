package com.xiesx.springboot.support.token;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class TokenStorageHelper {

	// 24小时后过期
	private final static int EXPIRE = 3600 * 12 * 2 * 7;

	public static TokenStorage createToken(String user_id) {
		// 生成一个token
		String token = IdWorker.get32UUID();
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		// 判断是否生成过token
		TokenStorage tokenEntity = new TokenStorage().setUserId(user_id).find();
		if (ObjectUtils.isEmpty(tokenEntity)) {
			tokenEntity = new TokenStorage();
			tokenEntity.setUserId(user_id);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.insert();
		} else {
			tokenEntity.setUpdateTime(now);
			tokenEntity.setToken(token);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.update();
		}
		return new TokenStorage().setUserId(user_id).find();
	}
}
