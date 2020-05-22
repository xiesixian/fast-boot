package com.xiesx.springboot.base.pagination;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @title PaginationResult.java
 * @description 分页返回对象
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
@Data
@Builder
@Accessors(fluent = true)
public class PaginationResult {

    @JSONField(ordinal = 1)
    public Integer code;

    @JSONField(ordinal = 2)
    public String msg;

    @JSONField(ordinal = 3)
    public List<?> data;

    @JSONField(ordinal = 4)
    public Integer count;
}
