package com.tongzhu.kittypay.wallet.wallet;



import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Wallet {

    @Id
    @Column(nullable = false)
    private String cusId;

    @Column(nullable = false)
    private BigDecimal balance;














    // ------ getters && setters -------
    // no constructors here. not the point of this microservice;

    public String getCusId() {
        return cusId;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
