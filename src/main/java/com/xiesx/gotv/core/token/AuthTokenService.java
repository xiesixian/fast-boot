package com.xiesx.gotv.core.token;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 * 
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class AuthTokenService {

	// 24小时后过期
	private final static int EXPIRE = 3600 * 12 * 2 * 7;

	public AuthToken queryByToken(String token) {
		return new AuthToken().setToken(token).find();
	}

	public AuthToken createToken(String user_id) {
		// 生成一个token
		String token = UUID.randomUUID().toString();
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		// 判断是否生成过token
		AuthToken tokenEntity = new AuthToken().setUserId(user_id).find();
		if (tokenEntity == null) {
			tokenEntity = new AuthToken();
			tokenEntity.setUserId(user_id);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.insert();
		} else {
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.update();
		}
		return new AuthToken().setUserId(user_id).find();
	}
}
