package com.xiesx.fastboot.support.generate.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class GeneratorDTO {

    @JSONField(ordinal = 101)
    public String author;

    @JSONField(ordinal = 102)
    public String packagePath;

    @JSONField(ordinal = 103)
    public String swagger2;

    @JSONField(ordinal = 104)
    public String activeRecord;

    @JSONField(ordinal = 105)
    public String baseResultMap;

    @JSONField(ordinal = 106)
    public String baseColumnList;

    @JSONField(ordinal = 107)
    public String enableCache;

    // ====================

    @JSONField(ordinal = 201)
    public String tablePrefixs;

    @JSONField(ordinal = 202)
    public String fieldPrefixs;

    @JSONField(ordinal = 203)
    public String superControllerClass;

    @JSONField(ordinal = 204)
    public String superServiceClass;

    @JSONField(ordinal = 205)
    public String superServiceImplClass;

    @JSONField(ordinal = 206)
    public String superMapperClass;

    @JSONField(ordinal = 207)
    public String superEntityClass;

    @JSONField(ordinal = 208)
    public String superEntityColumns;

    @JSONField(ordinal = 209)
    public String exclude;

    @JSONField(ordinal = 210)
    public String include;

    @JSONField(ordinal = 211)
    public String entityColumnConstant;

    @JSONField(ordinal = 212)
    public String entityTableFieldAnnotationEnable;

    @JSONField(ordinal = 213)
    public String entityBuilderModel;

    @JSONField(ordinal = 214)
    public String entityLombokModel;

    @JSONField(ordinal = 215)
    public String entityBooleanColumnRemoveIsPrefix;

    @JSONField(ordinal = 216)
    public String versionFieldName;

    @JSONField(ordinal = 217)
    public String logicDeleteFieldName;

    @JSONField(ordinal = 301)
    public String restControllerStyle;

    // ====================

    @JSONField(ordinal = 401)
    public String tplController;

    @JSONField(ordinal = 402)
    public String tplService;

    @JSONField(ordinal = 403)
    public String tplServiceImpl;

    @JSONField(ordinal = 404)
    public String tplMapper;

    @JSONField(ordinal = 405)
    public String tplXml;

    @JSONField(ordinal = 406)
    public String tplEntity;
}
