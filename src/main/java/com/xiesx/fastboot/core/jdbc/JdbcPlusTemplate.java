package com.xiesx.fastboot.core.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @title JdbcPlusTemplate.java
 * @description
 * @author Sixian.xie
 * @date 2020-7-21 22:32:10
 */

// https://blog.csdn.net/hbtj_1216/article/details/100532763?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1.3f686c96b88b49ab&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1.3f686c96b88b49ab
@Slf4j
@Component
public class JdbcPlusTemplate {

    /** 持久对象 */
    @Autowired
    private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return mNamedParameterJdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return mNamedParameterJdbcTemplate.getJdbcTemplate();
    }

    /**
     * 查询Map
     *
     * @param sql
     * @param args
     * @return
     */
    public Map<String, Object> queryForMap(String sql) {
        try {
            return mNamedParameterJdbcTemplate.queryForMap(sql, Maps.newLinkedHashMap());
        } catch (Exception e) {
            log.error("queryForMap error error", e);
            return null;
        }
    }

    public Map<String, Object> queryForMap(String sql, Class<?> dto) {
        try {
            return mNamedParameterJdbcTemplate.queryForMap(sql, new BeanPropertySqlParameterSource(dto));
        } catch (Exception e) {
            log.error("queryForMap error error", e);
            return null;
        }
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        try {
            return mNamedParameterJdbcTemplate.queryForMap(sql, paramMap);
        } catch (Exception e) {
            log.error("queryForMap error error", e);
            return null;
        }
    }

    public Map<String, Object> queryForMap(String sql, Object... args) {
        try {
            return mNamedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql, args);
        } catch (Exception e) {
            log.error("queryForMap error error", e);
            return null;
        }
    }

    /**
     * 查询List<Map>
     *
     * @param sql
     * @param args
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) {
        try {
            return mNamedParameterJdbcTemplate.queryForList(sql, Maps.newLinkedHashMap());
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    public List<Map<String, Object>> queryForList(String sql, Class<?> dto) {
        try {
            return mNamedParameterJdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(dto));
        } catch (Exception e) {
            log.error("queryForMap error error", e);
            return null;
        }
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
        try {
            return mNamedParameterJdbcTemplate.queryForList(sql, paramMap);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        try {
            return mNamedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql, args);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    /**
     * 查询Obj
     *
     * @param <T>
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    public <T> T queryForObj(String sql, Class<T> clazz) {
        try {
            return result(queryForMap(sql, Maps.newLinkedHashMap()), clazz);
        } catch (Exception e) {
            log.error("queryForMap error", e);
            return null;
        }
    }

    public <T> T queryForObj(String sql, Class<?> dto, Class<T> clazz) {
        try {
            return result(queryForMap(sql, dto), clazz);
        } catch (Exception e) {
            log.error("queryForMap error", e);
            return null;
        }
    }

    public <T> T queryForObj(String sql, Map<String, ?> paramMap, Class<T> clazz) {
        try {
            return result(queryForMap(sql, paramMap), clazz);
        } catch (Exception e) {
            log.error("queryForMap error", e);
            return null;
        }
    }

    public <T> T queryForObj(String sql, Object[] args, Class<T> clazz) {
        try {
            return result(queryForMap(sql, args), clazz);
        } catch (Exception e) {
            log.error("queryForMap error", e);
            return null;
        }
    }

    /**
     * 查询List<Obj>
     *
     * @param <T>
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    public <T> List<T> queryForListObj(String sql, Class<T> clazz) {
        try {
            return result(queryForList(sql, Maps.newLinkedHashMap()), clazz);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    public <T> List<T> queryForListObj(String sql, Class<?> dto, Class<T> clazz) {
        try {
            return result(queryForList(sql, dto), clazz);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    public <T> List<T> queryForListObj(String sql, Map<String, ?> paramMap, Class<T> clazz) {
        try {
            return result(queryForList(sql, paramMap), clazz);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    public <T> List<T> queryForListObj(String sql, Object[] args, Class<T> clazz) {
        try {
            return result(queryForList(sql, args), clazz);
        } catch (Exception e) {
            log.error("queryForList error", e);
            return null;
        }
    }

    /**
     * 执行update
     *
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql) {
        try {
            return mNamedParameterJdbcTemplate.update(sql, Maps.newLinkedHashMap());
        } catch (Exception e) {
            log.error("update error", e);
            return 0;
        }
    }

    public int update(String sql, Map<String, ?> paramMap) {
        try {
            return mNamedParameterJdbcTemplate.update(sql, paramMap);
        } catch (Exception e) {
            log.error("update error", e);
            return 0;
        }
    }

    public int update(String sql, Object... args) {
        try {
            return getJdbcTemplate().update(sql, args);
        } catch (Exception e) {
            log.error("update error", e);
            return 0;
        }
    }

    //

    /**
     * 数据填充
     *
     * @param map
     * @return
     */
    protected <T> T result(Map<String, Object> map, Class<T> clazz) {
        if (ObjectUtils.isEmpty(map)) {
            return null;
        }
        return JSON.toJavaObject(new JSONObject(map), clazz);
    }

    /**
     * 数据填充
     *
     * @param list
     * @return
     */
    protected <T> List<T> result(List<Map<String, Object>> list, Class<T> clazz) {
        List<T> data = Lists.newArrayList();
        for (Map<String, Object> map : list) {
            data.add(result(map, clazz));
        }
        return data;
    }
}
