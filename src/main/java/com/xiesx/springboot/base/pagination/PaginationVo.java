package com.xiesx.springboot.base.pagination;

import lombok.Data;

/**
 * @title PaginationVo.java
 * @description 分页请求对象
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
@Data
public class PaginationVo {

    public Integer page = 0;

    public Integer limit = 25;

    public Integer size = 25;

    public Integer getPage() {
        return page - 1;
    }
}
