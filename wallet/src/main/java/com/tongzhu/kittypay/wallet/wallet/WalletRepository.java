package com.tongzhu.kittypay.wallet.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {



    @Modifying
    @Query(value = """
            UPDATE wallet
            SET balance = balance - :amount
            WHERE cus_id = :cusId AND balance >= :amount;
            """, nativeQuery = true)
    public int tryDeduct(String cusId, BigDecimal amount);


}
