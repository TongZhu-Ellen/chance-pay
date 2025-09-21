package com.tongzhu.chancepay.orchestrator.request;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Request {




    @Id
    private String uuid;
    private String cusId;
    private BigDecimal amount;
    private Instant createdAt;


    private Boolean succeed;








    public Request() {
    }
    public Request(String uuid, String cusId, BigDecimal amount, Instant createdAt) {
        this.uuid = uuid;
        this.cusId = cusId;
        this.amount = amount;
        this.createdAt = createdAt;

    }







    public BigDecimal getAmount() {
        return amount;
    }
    public String getUuid() {
        return uuid;
    }
    public String getCusId() {
        return cusId;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Boolean getSucceed() {
        return succeed;
    }












}
