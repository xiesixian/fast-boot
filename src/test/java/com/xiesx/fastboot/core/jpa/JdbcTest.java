package com.xiesx.fastboot.core.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiesx.fastboot.FastBootApplication;
import com.xiesx.fastboot.core.jpa.identifier.IdWorker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastBootApplication.class)
public class JdbcTest {

    @Autowired
    SLogRepository mLogDao;

    @Test
    public void delete() {

        Long id = Long.parseLong("47682765939806208");
        List<Long> ids = Lists.newArrayList(id);
        mLogDao.deleteById(id);

        mLogDao.delete(mLogDao.findById(id).get());

        mLogDao.deleteAll(mLogDao.findAllById(ids));

        mLogDao.deleteAll();
    }

    @Test
    public void find() {

        int totile = 6;

        Long id = Long.parseLong("47682765939806208");
        List<Long> ids = Lists.newArrayList(id);
        boolean b1 = mLogDao.existsById(id);
        assertThat(b1).isTrue();
        boolean b2 = mLogDao.existsById(1L);
        assertThat(b2).isFalse();

        Optional<SLog> log = mLogDao.findById(id);
        assertThat(log.get().getId()).isEqualTo(id);

        List<SLog> logs = mLogDao.findAllById(ids);
        assertThat(logs.size()).isEqualTo(1);
        assertThat(logs.get(0).getId()).isEqualTo(id);

        List<SLog> logAll = mLogDao.findAll();
        assertThat(logAll.size()).isEqualTo(totile);

        long count = mLogDao.count();
        assertThat(count).isEqualTo(totile);
    }

    @Test
    public void page_sort() {}

    @Test
    public void query() {
        List<SLog> logMethods1 = mLogDao.findByMethod("list");
        List<SLog> logMethods2 = mLogDao.queryByMethodSQL("list");
        List<SLog> logMethods3 = mLogDao.queryByMethodSQL("list");
        assertThat(3).isEqualTo(logMethods1.size()).isEqualTo(logMethods2.size()).isEqualTo(logMethods3.size());
    }

    @Test
    public void save() {
        // 添加
        SLog slog = new SLog();
        slog.setId(IdWorker.getID());
        slog.setCreateDate(new Date());
        slog.setIp("1:2:3:4");
        slog.setMethod("save");
        slog.setType("post");
        slog.setUrl("xxx/save");
        slog.setReq("param");
        slog.setRes("resule");
        slog.setT(1L);
        slog.setOpt("1");
        slog = mLogDao.save(slog);
        log.info("=============" + JSON.toJSONString(slog, true));
        assertThat(slog.getMethod()).isEqualTo("save");

        //
        List<SLog> logs = Lists.newArrayList();
        // 批量添加
        for (int i = 1; i < 3; i++) {
            slog = new SLog();
            slog.setId(IdWorker.getID());
            slog.setCreateDate(new Date());
            slog.setIp("1:2:3:4");
            slog.setMethod("saveAll" + i);
            slog.setType("post");
            slog.setUrl("xxx/saveAll");
            slog.setReq("param");
            slog.setRes("resule");
            slog.setT(1L);
            slog.setOpt("1");
            logs.add(slog);
        }
        List<SLog> slogs = mLogDao.saveAll(logs);
        log.info("=============" + JSON.toJSONString(slog, true));
        assertThat(slogs.get(1).getMethod()).isEqualTo("saveAll2");
    }

    @Test
    public void update() {
        Long id = Long.parseLong("47682765939806208");
        String ip = "1.2.3.4.5";
        mLogDao.updateIpById(ip, id);
        assertThat(mLogDao.findById(id).get().getIp()).isEqualTo(ip);
    }
}
