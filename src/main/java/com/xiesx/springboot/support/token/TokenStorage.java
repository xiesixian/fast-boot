package com.xiesx.springboot.support.token;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiesx.springboot.base.jdbc.JdbcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @title Token.java (generator)
 * @description 系统用户Token
 * @author 谢思贤
 * @date 2019-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = TokenStorage.TABLE.SYS_TOKEN)
public class TokenStorage extends JdbcEntity<TokenStorage> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String userId;

	/** token */
	@Column
	private String token;

	/** 更新时间 */
	@Column(name = "update_time")
	private Date updateTime;

	/** 过期时间 */
	@Column(name = "expire_time")
	private Date expireTime;

	/** 常量 */
	public class TABLE {

		public static final String SYS_TOKEN = "sys_token";
	}

	
	/** 常量 */
	public class FIELDS {

		public static final String USER_ID = "user_id";

		public static final String TOKEN = "token";

		public static final String UPDATE_TIME = "update_time";

		public static final String EXPIRE_TIME = "expire_time";
	}

	/** 主键值 */
	protected Serializable pkVal() {
		return this.userId;
	}
}
