package com.xiesx.fastboot.base.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;
import com.xiesx.fastboot.base.pagination.PaginationResult;

import lombok.NonNull;

/**
 * @title PaginationHelper.java
 * @description 分页帮助类
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
public class PaginationHelper {

    /**
     * 构造
     * 
     * @param page
     * @return
     */
    public static PaginationResult create(Page<?> page) {
        return create(page.toList(), (int) page.getTotalElements());
    }

    /**
     * 构造
     *
     * @param data
     * @return
     */
    public static PaginationResult create(@NonNull List<?> data) {
        return create(data, null);
    }

    /**
     * 构造
     *
     * @param data
     * @param total
     * @return
     */
    public static PaginationResult create(@NonNull List<?> data, Integer total) {
        List<?> list = Lists.newArrayList(data);
        if (list.isEmpty()) {
            return PaginationResult.builder().code(1).msg("无数据").data(Lists.newArrayList()).count(0).build();
        } else {
            return PaginationResult.builder().code(0).data(list).count(total).build();
        }
    }
}
