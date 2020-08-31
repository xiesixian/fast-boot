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
    public void map_obj() {
        // 1、============ ============
        String sql = "select count(1) as ct from sys_region";
        // 1.1
        Map<String, Object> map = mJdbcTemplatePlus.queryForMap(sql);
        log.info("queryForMap(sql) : {} : {}", JSON.toJSONString(map), map.getOrDefault("ct", 0));
        // 1.2
        CommonDto comm = mJdbcTemplatePlus.queryForObj(sql, CommonDto.class);
        log.info("queryForObj(sql, clazz) : {} : {}", JSON.toJSONString(comm), comm.getCt());

        // 2、============ dto ============
        TestDto dto = new TestDto();
        dto.setId(1);
        sql = "select id, name from sys_region where id = :id";
        // 2.1
        map = mJdbcTemplatePlus.queryForMap(sql, dto);
        log.info("queryForMap(sql, dto) : {} : {}", JSON.toJSONString(map), map.get("name"));
        // 2.2
        TestDto obj = mJdbcTemplatePlus.queryForObj(sql, dto, TestDto.class);
        log.info("queryForObj(sql, dto, clazz) : {} : {}", JSON.toJSONString(obj), obj.getName());

        // 3、============ param ============
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", 1);
        sql = "select * from sys_region where id = :id";
        // 3.1
        map = mJdbcTemplatePlus.queryForMap(sql, param);
        log.info("queryForMap(sql, param) : {} : {}", JSON.toJSONString(map), map.get("name"));
        // 3.2
        obj = mJdbcTemplatePlus.queryForObj(sql, param, TestDto.class);
        log.info("queryForObj(sql, param, clazz) : {} : {}", JSON.toJSONString(obj), obj.getName());
    }

    @Test
    public void list_map_obj() {
        // 1、============ ============
        String sql = "select * from sys_region limit 10";
        // 1.1
        List<Map<String, Object>> list_map = mJdbcTemplatePlus.queryForList(sql);
        log.info("queryForList(sql) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        // 1.2
        List<TestDto> list_obj = mJdbcTemplatePlus.queryForListObj(sql, TestDto.class);
        log.info("queryForListObj(sql, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());

        // 2、============ dto ============
        TestDto dto = new TestDto();
        dto.setParent(-1);
        sql = "select id, name from sys_region where parent = :parent";
        // 2.1
        list_map = mJdbcTemplatePlus.queryForList(sql, dto);
        log.info("queryForList(sql, dto) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        // 2.2
        list_obj = mJdbcTemplatePlus.queryForListObj(sql, dto, TestDto.class);
        log.info("queryForListObj(sql, dto, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());

        // 3、============ param ============
        Map<String, Object> param = Maps.newHashMap();
        param.put("parent", -1);
        sql = "select * from sys_region where parent = :parent";
        // 3.1
        list_map = mJdbcTemplatePlus.queryForList(sql, param);
        log.info("queryForList(sql, param) : {} : {}", list_map.size(), list_map.get(0).get("name"));
        // 3.2
        list_obj = mJdbcTemplatePlus.queryForListObj(sql, param, TestDto.class);
        log.info("queryForListObj(sql, param, clazz) : {} : {}", list_obj.size(), list_obj.get(0).getName());
    }

    @Test
    public void update() {
        // 1、============ insert ============
        String sql = "INSERT INTO sys_region (`id`,`name`,`name_short`,`code`) VALUES (:id,:name,:name_short,:code)";
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", 100000);
        param.put("name", "测试添加");
        param.put("name_short", "test");
        param.put("code", "12345");
        log.info("insert(sql, param) row : {}", mJdbcTemplatePlus.update(sql, param));

        // 2、============ update ============
        // 2.1、sql
        sql = "update sys_region SET sort = 2 where id = 100000";
        log.info("update(sql) row : {}", mJdbcTemplatePlus.update(sql));
        // 2.2、dto
        sql = "update sys_region SET sort = :sort where id = :id";
        TestDto dto = new TestDto();
        dto.setSort(3);
        dto.setId(100000);
        log.info("update(sql, dto) row : {}", mJdbcTemplatePlus.update(sql, dto));
        // 2.2、param
        sql = "update sys_region SET sort = :sort where id = :id";
        param = Maps.newHashMap();
        param.put("sort", 1);
        param.put("id", 100000);
        log.info("update(sql, param) row : {}", mJdbcTemplatePlus.update(sql, param));

        // 3、============ delete ============
        sql = "delete from sys_region where id = 100000";
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
