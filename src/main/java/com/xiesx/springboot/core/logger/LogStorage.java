package com.xiesx.springboot.core.logger;

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
 * @title Log.java (generator)
 * @description 日志存储表
 * @author 谢思贤
 * @date 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = LogStorage.TABLE.SYS_LOG)
public class LogStorage extends JdbcEntity<LogStorage> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	private String id;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 请求IP
	 */
	@Column
	private String ip;

	/**
	 * 方法
	 */
	@Column
	private String method;

	/**
	 * 方式
	 */
	@Column(name = "TYPE")
	private String type;

	/**
	 * 地址
	 */
	@Column
	private String url;

	/**
	 * 请求
	 */
	@Column
	private String req;

	/**
	 * 响应
	 */
	@Column
	private String res;

	/**
	 * 执行时间（毫秒）
	 */
	@Column
	private Long t;
	
	/**
	 * 执行时间（毫秒）
	 */
	@Column
	private String opt;

	/** 常量 */
	public class TABLE {

		public static final String SYS_LOG = "sys_log";
	}

	public class FIELDS {

		public static final String ID = "id";

		public static final String CREATE_TIME = "create_time";

		public static final String IP = "ip";

		public static final String METHOD = "method";

		public static final String TYPE = "TYPE";

		public static final String URL = "url";

		public static final String REQ = "req";

		public static final String RES = "res";

		public static final String T = "t";
		
		public static final String OPT = "opt";	
	}

	protected Serializable pkVal() {
		return this.id;
	}
}