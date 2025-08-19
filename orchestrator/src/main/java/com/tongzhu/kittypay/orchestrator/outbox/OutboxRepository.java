package com.tongzhu.kittypay.orchestrator.outbox;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OutboxRepository extends JpaRepository<Outbox, String> {

    @Query(value = """ 
            SELECT * FROM outbox
            WHERE acked = false OR returned = true
            ORDER BY last_touched_at ASC 
            LIMIT 1
            """, nativeQuery = true)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Outbox selectOneToPost();


    @Modifying
    @Query(value = """
            DELETE FROM outbox
            WHERE acked = true AND returned = false 
            AND last_touched_at <= NOW() - INTERVAL 2 SECOND
            ORDER BY last_touched_at ASC
            LIMIT 5
            """, nativeQuery = true)
    public int deleteFive();


    @Modifying
    @Query(value = """
            UPDATE outbox
            SET acked = true
            WHERE uuid = :uuid
            """, nativeQuery = true)
    void markAcked(String uuid);


    @Modifying
    @Query(value = """
            UPDATE outbox
            SET returned = true
            WHERE uuid = :uuid
            """, nativeQuery = true)
    void markReturned(String uuid);
}
