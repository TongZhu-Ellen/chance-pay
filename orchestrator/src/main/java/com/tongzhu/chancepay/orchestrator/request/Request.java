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

    @Enumerated(EnumType.STRING)
    private RequestStatus status;








    public Request() {
    }
    public Request(String uuid, String cusId, BigDecimal amount, Instant createdAt) {
        this.uuid = uuid;
        this.cusId = cusId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.status = RequestStatus.PENDING;
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
    public RequestStatus getStatus() {
        return status;
    }












}
