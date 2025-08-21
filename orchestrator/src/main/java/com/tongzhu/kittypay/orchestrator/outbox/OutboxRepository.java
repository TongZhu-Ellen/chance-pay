package com.tongzhu.kittypay.orchestrator.outbox;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, String> {

    @Transactional()
    @Modifying
    @Query(value = """
            UPDATE outbox
              SET try_count = try_count + 1,
                  last_touched_at = CURRENT_TIMESTAMP
              WHERE uuid = :uuid
              """, nativeQuery = true)
    public int touchOnce(String uuid);



    @Query(value = """
            SELECT * FROM outbox
            WHERE try_count < 5 AND last_touched_at < (CURRENT_TIMESTAMP - INTERVAL 5 SECOND)
            ORDER BY last_touched_at ASC
            LIMIT 5
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    public List<Outbox> selectFiveToPublish();








}
