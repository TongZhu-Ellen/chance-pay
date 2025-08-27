package com.tongzhu.chancepay.wallet.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {


    @Modifying
    @Query(value = """
            UPDATE wallet
            SET balance = balance - :amount
            WHERE cus_id = :cusId AND balance >= :amount
            """, nativeQuery = true)
    int tryDeduct(@Param("cusId") String cusId, @Param("amount") BigDecimal amount);
}
