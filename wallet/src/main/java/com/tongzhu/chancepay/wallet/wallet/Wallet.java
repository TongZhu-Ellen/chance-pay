package com.tongzhu.chancepay.wallet.wallet;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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








    public String getCusId() {
        return cusId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
}
