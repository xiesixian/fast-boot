package com.xiesx.fastboot.core.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SLogRepository extends JpaPlusRepository<SLog, Long> {

    List<SLog> findByMethod(String method);

    @Query("from SLog where method=?1")
    List<SLog> queryByMethodHQL(String method);

    @Query(value = "select * from sys_log where method=:method", nativeQuery = true)
    List<SLog> queryByMethodSQL(@Param("method") String method);

    @Transactional
    @Modifying
    @Query("update SLog set ip=?1 where id=?2")
    void updateIpById(String ip, Long id);
}
