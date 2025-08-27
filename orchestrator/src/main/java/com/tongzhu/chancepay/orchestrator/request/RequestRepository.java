package com.tongzhu.chancepay.orchestrator.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends JpaRepository<Request, String> {


    @Modifying
    @Query(value = """
            UPDATE request
            SET status = 'SUCCEED'
            WHERE uuid = :uuid AND status = 'PENDING'
            """, nativeQuery = true)
    public int setSucceed(@Param("uuid") String uuid);

    @Modifying
    @Query(value = """
            UPDATE request
            SET status = 'FAILED'
            WHERE uuid = :uuid AND status = 'PENDING'
            """, nativeQuery = true)
    public int setFailed(@Param("uuid") String uuid);
}
