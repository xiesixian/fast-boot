package com.xiesx.gotv.core.logger;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.xiesx.gotv.core.jdbc.JdbcEntity;

/**
 * @title APILog.java (generator)
 * @description
 * @author 谢思贤
 * @date 2019-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = LogStorage.TABLE.GO_LOG)
public class LogStorage extends JdbcEntity<LogStorage> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "create_date")
	private Date createDate;

	@Column
	private String ip;

	@Column
	private String method;

	@Column
	private String type;

	@Column
	private String url;

	@Column
	private String req;

	@Column
	private String res;

	@Column
	private Long t;

	/** 常量 */
	public class TABLE {

		public static final String GO_LOG = "go_log";
	}

	public class FIELDS {

		public static final String ID = "id";

		public static final String CREATE_DATE = "create_date";

		public static final String IP = "ip";

		public static final String METHOD = "method";

		public static final String TYPE = "type";

		public static final String URL = "url";

		public static final String REQ = "req";

		public static final String RES = "res";

		public static final String T = "t";
	}

	/** 主键值 */
	protected Serializable pkVal() {
		return this.id;
	}

}
