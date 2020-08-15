package com.xiesx.fastboot.core.jpa;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.xiesx.fastboot.core.jpa.entity.JpaPlusEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title Log.java (generator)
 * @description 日志存储表
 * @author 谢思贤
 * @date 2020-04-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants(innerTypeName = "FIELDS")
@Table(name = "sys_log")
@Entity
@DynamicInsert
@DynamicUpdate
public class SLog extends JpaPlusEntity<SLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.xiesx.fastboot.core.jpa.identifier.IdWorkerGenerator")
    private Long id;

    /**
     * 创建时间
     */
    @Column
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
     * 操作人
     */
    @Column
    private String opt;
}
