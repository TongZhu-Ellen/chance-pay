package com.tongzhu.chancepay.orchestrator.outbox;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface OutboxRepository extends JpaRepository<Outbox, String> {


    @Modifying
    @Query(value = """
            UPDATE outbox
              SET try_count = try_count + 1,
                  last_touched_at = CURRENT_TIMESTAMP
              WHERE uuid = :uuid
              """, nativeQuery = true)
    public int touchOnce(@Param("uuid") String uuid);



    @Query(value = """
            SELECT * FROM outbox
            WHERE try_count < 5 AND last_touched_at < (CURRENT_TIMESTAMP - INTERVAL 2 SECOND)
            ORDER BY last_touched_at ASC
            LIMIT 5
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    public List<Outbox> selectFiveToPublish();








}
