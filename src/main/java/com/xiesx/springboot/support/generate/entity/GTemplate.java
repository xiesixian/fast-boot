package com.xiesx.springboot.support.generate.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.xiesx.springboot.core.jpa.entity.JpaPlusEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * @title Generator.java (generator)
 * @description 代码生成
 * @author 谢思贤
 * @date 2020-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants(innerTypeName = "FIELDS")
public class GTemplate extends JpaPlusEntity<GTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 控制层
     */
    @Column
    private String controller;

    /**
     * 业务层
     */
    @Column
    private String service;

    /**
     * 业务层-实现
     */
    @Column
    private String serviceImpl;

    /**
     * 持久层
     */
    @Column
    private String mapper;

    /**
     * 持久层xml
     */
    @Column
    private String xml;

    /**
     * 实体层
     */
    @Column
    private String entity;

    /**
     * 排序
     */
    @Column
    private Integer sort;
}
