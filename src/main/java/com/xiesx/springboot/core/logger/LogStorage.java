package com.xiesx.springboot.core.logger;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xiesx.springboot.core.jdbc.JdbcPlusEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title Log.java (generator)
 * @description 日志存储表
 * @author 谢思贤
 * @date 2019-12-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(innerTypeName = "FIELDS")
@Table(name = LogStorage.TABLE)
public class LogStorage extends JdbcPlusEntity<LogStorage> {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "sys_log";

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
    @Column
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

    protected Serializable pkVal() {
        return this.id;
    }
}
