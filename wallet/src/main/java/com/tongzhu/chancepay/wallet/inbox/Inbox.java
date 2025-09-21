package com.tongzhu.chancepay.wallet.inbox;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Inbox {


    @Id
    private String uuid;
    private Boolean succeed;



    public String getUuid() {
        return uuid;
    }
    public Boolean getSucceed() {
        return succeed;
    }















}
