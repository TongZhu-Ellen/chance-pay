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
            INSERT IGNORE INTO inbox(uuid, status) VALUES (:uuid, 'PENDING');
            """, nativeQuery = true)
    public int insertIgnore(@Param("uuid") String uuid);


    @Modifying
    @Query(value = "UPDATE inbox SET status = :status WHERE uuid = :uuid", nativeQuery = true)
    int updateStatus(@Param("uuid") String uuid, @Param("status") String status);



}
