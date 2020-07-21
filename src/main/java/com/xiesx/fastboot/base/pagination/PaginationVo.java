package com.xiesx.fastboot.base.pagination;

import lombok.Data;

/**
 * @title PaginationVo.java
 * @description 分页请求对象
 * @author Sixian.xie
 * @date 2020-7-21 22:29:45
 */
@Data
public class PaginationVo {

    public Integer page = 1;

    public Integer limit = 25;

    public Integer size = 25;

    public Integer getPage() {
        return page - 1;
    }
}
