package com.tongzhu.kittypay.wallet.inbox;

import com.tongzhu.kittypay.wallet.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InboxRepository extends JpaRepository<Inbox, String> {



    @Modifying
    @Query(value = """
            INSERT IGNORE INTO inbox(uuid, status) VALUES (:uuid, 'PENDING');
            """, nativeQuery = true)
    public int insertIgnore(@Param("uuid") String uuid);


}
