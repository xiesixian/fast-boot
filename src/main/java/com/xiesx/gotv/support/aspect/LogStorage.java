package com.xiesx.gotv.support.aspect;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.xiesx.gotv.support.datebase.jdbc.base.BaseEntity;

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
public class LogStorage extends BaseEntity<LogStorage> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "create_time")
	private Date createTime;

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

		public static final String MODIFY_DATE = "modify_date";

		public static final String REQUEST_PARAM = "request_param";

		public static final String RESPONSE_PARAM = "response_param";

		public static final String EXECUTE_TIME = "execute_time";

		public static final String TARGET = "target";

		public static final String REMARK = "remark";
	}

	/** 主键值 */
	protected Serializable pkVal() {
		return this.id;
	}

}
