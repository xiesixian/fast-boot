package com.xiesx.fastboot.core.jdbc;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiesx.fastboot.FastBootApplication;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastBootApplication.class)
public class JdbcTemplatePlusTest {

    @Autowired
    JdbcTemplatePlus mJdbcTemplatePlus;

    @Test
    public void map_SQL() {
        String sql = "select count(1) as ct from sys_region";
        Map<String, Object> map = mJdbcTemplatePlus.queryForMap(sql);
        log.info("queryForMap(sql) : {} : {}", JSON.toJSONString(map), map.getOrDefault("ct", 0));
        CommonDto comm = mJdbcTemplatePlus.queryForMap(sql, CommonDto.class);
        log.info("queryForMap(sql, clazz) : {} : {}", JSON.toJSONString(comm), comm.getCt());
    }

    @Test
    public void map_DTO() {
        TestDto dto = new TestDto();
        dto.setId(1);
        String sql = "select id, name from sys_region where id = :id";
        Map<String, Object> map = mJdbcTemplatePlus.queryForMap(sql, dto);
        log.info("queryForMap(sql, dto) : {} : {}", JSON.toJSONString(map), map.get("name"));
        TestDto obj = mJdbcTemplatePlus.queryForMap(sql, dto, TestDto.class);
        log.info("queryForMap(sql, dto, clazz) : {} : {}", JSON.toJSONString(obj), obj.getName());
    }

    @Test
    public void map_MAP() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", 1);
        String sql = "select * from sys_region where id = :id";
        Map<String, Object> map = mJdbcTemplatePlus.queryForMap(sql, param);
        log.info("queryForMap(sql, param) : {} : {}", JSON.toJSONString(map), map.get("name"));
        TestDto obj = mJdbcTemplatePlus.queryForMap(sql, param, TestDto.class);
        log.info("queryForMap(sql, param, clazz) : {} : {}", JSON.toJSONString(obj), obj.getName());
    }

    @Test
    public void list_SQL() {
        String sql = "select * from sys_region limit 10";
        List<Map<String, Object>> list_map = mJdbcTemplatePlus.queryForList(sql);
        log.info("queryForList(sql) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        List<TestDto> list_obj = mJdbcTemplatePlus.queryForList(sql, TestDto.class);
        log.info("queryForList(sql, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());
    }

    @Test
    public void list_DTO() {
        TestDto dto = new TestDto();
        dto.setParent(-1);
        String sql = "select id, name from sys_region where parent = :parent";
        List<Map<String, Object>> list_map = mJdbcTemplatePlus.queryForList(sql, dto);
        log.info("queryForList(sql, dto) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        List<TestDto> list_obj = mJdbcTemplatePlus.queryForList(sql, dto, TestDto.class);
        log.info("queryForList(sql, dto, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());
    }

    @Test
    public void list_MAP() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("parent", -1);
        String sql = "select * from sys_region where parent = :parent";
        List<Map<String, Object>> list_map = mJdbcTemplatePlus.queryForList(sql, param);
        log.info("queryForList(sql, param) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        List<TestDto> list_obj = mJdbcTemplatePlus.queryForList(sql, param, TestDto.class);
        log.info("queryForList(sql, param, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());
    }

    @Test
    public void insert() {
        String sql = "INSERT INTO sys_region (`id`,`name`,`name_short`,`code`) VALUES (:id,:name,:name_short,:code)";
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", 100000);
        param.put("name", "测试添加");
        param.put("name_short", "test");
        param.put("code", "12345");
        log.info("insert(sql, param) row : {}", mJdbcTemplatePlus.update(sql, param));
    }

    @Test
    public void update_SQL() {
        String sql = "update sys_region SET sort = 2 where id = 100000";
        log.info("update(sql) row : {}", mJdbcTemplatePlus.update(sql));
    }

    @Test
    public void update_DTO() {
        String sql = "update sys_region SET sort = :sort where id = :id";
        TestDto dto = new TestDto();
        dto.setSort(3);
        dto.setId(100000);
        log.info("update(sql, dto) row : {}", mJdbcTemplatePlus.update(sql, dto));
    }

    @Test
    public void update_MAP() {
        String sql = "update sys_region SET sort = :sort where id = :id";
        Map<String, Object> param = Maps.newHashMap();
        param.put("sort", 1);
        param.put("id", 100000);
        log.info("update(sql, param) row : {}", mJdbcTemplatePlus.update(sql, param));
    }

    @Test
    public void delete() {
        String sql = "delete from sys_region where id = 100000";
        log.info("delete(sql) row : {}", mJdbcTemplatePlus.update(sql));
    }

    @Data
    public static class TestDto {

        public Integer id;

        public String name;

        public Integer parent;

        public Integer sort;
    }

    @Data
    public static class CommonDto {

        public Integer ct;
    }
}
