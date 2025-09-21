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
            SET succeed = :succeed
            WHERE uuid = :uuid AND succeed IS NULL
            """, nativeQuery = true)
    public int setSucceed(@Param("uuid") String uuid, @Param("succeed") Boolean succeed);


}
