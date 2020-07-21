package com.xiesx.fastboot.support.generate.entity;

import javax.persistence.Column;
import javax.persistence.Id;

import com.xiesx.fastboot.core.jdbc.entity.JdbcPlusEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title GConfig.java
 * @description 代码配置
 * @author Sixian.xie
 * @date 2020-7-21 22:40:43
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants(innerTypeName = "FIELDS")
public class GConfig extends JdbcPlusEntity<GConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 模板ID
     */
    @Column
    private String templateId;

    /**
     * 用户ID
     */
    @Column
    private Long userId;

    /**
     * 数据配置
     */
    @Column
    private String config;
}
