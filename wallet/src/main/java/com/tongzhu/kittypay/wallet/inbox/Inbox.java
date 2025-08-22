package com.tongzhu.kittypay.wallet.inbox;

import com.tongzhu.kittypay.wallet.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;


@Entity
public class Inbox {

    @Id
    private String uuid;

    @Enumerated(EnumType.STRING)
    private Status status;




    public void setStatus(Status status) {
        this.status = status;
    }








}
