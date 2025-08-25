package com.tongzhu.chancepay.orchestrator.request;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.annotation.Id;

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


}
