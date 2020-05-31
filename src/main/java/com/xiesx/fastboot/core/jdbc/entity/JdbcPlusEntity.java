package com.xiesx.fastboot.core.jdbc.entity;

import java.io.Serializable;

public class JdbcPlusEntity<T> implements Serializable {

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    // /** 实体对象 */
    // private Class<T> entityClass;
    //
    // /** 持久对象 */
    // private JdbcPlusTemplate mJdbcPlusTemplate;

    /**
     * 获取运行时的具体实体对象
     */
    public JdbcPlusEntity() {
        if (getClass().getGenericSuperclass() instanceof JdbcPlusEntity) {
            // Type superclass = getClass().getGenericSuperclass();
            // ParameterizedType type = (ParameterizedType) superclass;
            // entityClass = (Class<T>) type.getActualTypeArguments()[0];
        }
        // mJdbcPlusTemplate = SpringHelper.getBean(JdbcPlusTemplate.class);
    }
}
