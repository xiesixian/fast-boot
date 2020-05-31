package com.xiesx.fastboot.support.generate.entity;

import javax.persistence.Column;
import javax.persistence.Id;

import com.xiesx.fastboot.core.jdbc.entity.JdbcPlusEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title GeneratorConfig.java (generator)
 * @description 代码生成
 * @author 谢思贤
 * @date 2020-04-27
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
