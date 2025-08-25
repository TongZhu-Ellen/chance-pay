package com.tongzhu.chancepay.wallet.wallet;



import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Wallet {

    @Id
    private String cusId;
    private BigDecimal balance;





    public boolean hasEnough(BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }

    public BigDecimal deduct(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
        return this.balance;
    }
}
