package com.tongzhu.chancepay.wallet.inbox;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Inbox {


    @Id
    private String uuid;
    private String status;









    public String getUuid() {
        return uuid;
    }
    public String getStatus() {
        return status;
    }














}
