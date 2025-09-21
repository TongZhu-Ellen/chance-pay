package com.tongzhu.chancepay.wallet.inbox;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InboxRepository extends JpaRepository<Inbox, String> {



    @Modifying
    @Query(value = """
            INSERT IGNORE INTO inbox(uuid, succeed) VALUES (:uuid, NULL);
            """, nativeQuery = true)
    public int insertIgnore(@Param("uuid") String uuid);


    @Modifying
    @Query(value = """
            UPDATE inbox
            SET succeed = :succeed 
            WHERE uuid = :uuid && succeed IS NULL
            """, nativeQuery = true)
    int updateStatus(@Param("uuid") String uuid, @Param("succeed") Boolean succeed);



}
