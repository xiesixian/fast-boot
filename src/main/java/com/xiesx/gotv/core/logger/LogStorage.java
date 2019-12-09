package com.xiesx.gotv.core.logger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.xiesx.gotv.support.jdbc.JdbcEntity;

/**
 * @title Log.java (generator)
 * @description 日志存储表
 * @author 谢思贤
 * @date 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = LogStorage.TABLE.GO_LOG)
@ApiModel(value = "Log", description = "日志存储表")
public class LogStorage extends JdbcEntity<LogStorage> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "id")
	private String id;

	@ApiModelProperty(value = "创建时间")
	@Column
	private Date createDate;

	@ApiModelProperty(value = "请求IP")
	@Column
	private String ip;

	@ApiModelProperty(value = "方法")
	@Column
	private String method;

	@ApiModelProperty(value = "方式")
	@Column(name = "TYPE")
	private String type;

	@ApiModelProperty(value = "地址")
	@Column
	private String url;

	@ApiModelProperty(value = "请求")
	@Column
	private String req;

	@ApiModelProperty(value = "响应")
	@Column
	private String res;

	@ApiModelProperty(value = "执行时间（毫秒）")
	@Column
	private Long t;

	/** 常量 */
	public class TABLE {

		public static final String GO_LOG = "go_log";
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
	}

	protected Serializable pkVal() {
		return this.id;
	}
}