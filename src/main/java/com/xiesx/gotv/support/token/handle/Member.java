package com.xiesx.gotv.support.token.handle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiesx.gotv.support.datebase.mpbatis.base.BmpEntity;

/**
 * @title Member.java
 * @description
 * @author Sixian.xie
 * @date 2018年12月21日 下午6:14:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ymp_op_member")
public class Member extends BmpEntity<Member> {

	private static final long serialVersionUID = 1L;

	private String nickname;

	private String mobile;

	private String qq;

	private Long point;

	private BigDecimal balance;

	private BigDecimal amount;

	private String address;

	private Long memberRank;

	private Boolean isEnabled;

	private Boolean isLocked;

	private String currentToken;

	private LocalDateTime lastLoginDate;

	/** 常量 */
	public class FIELDS {

		public static final String ID = "id";

		public static final String CREATE_DATE = "create_date";

		public static final String MODIFY_DATE = "modify_date";

		public static final String NICKNAME = "nickname";

		public static final String MOBILE = "mobile";

		public static final String QQ = "qq";

		public static final String POINT = "point";

		public static final String BALANCE = "balance";

		public static final String AMOUNT = "amount";

		public static final String ADDRESS = "address";

		public static final String MEMBER_RANK = "member_rank";

		public static final String IS_ENABLED = "is_enabled";

		public static final String IS_LOCKED = "is_locked";

		public static final String CURRENT_TOKEN = "current_token";

		public static final String LAST_LOGIN_DATE = "last_login_date";
	}
}
