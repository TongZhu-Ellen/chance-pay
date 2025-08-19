package com.tongzhu.kittypay.orchestrator.request;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

import static com.tongzhu.kittypay.orchestrator.request.RequestStatus.PENDING;

@Entity
public class Request {

    @Id
    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String cusId;

    @Column(nullable = false)
    private BigDecimal amount; // potentially this can be negative;

    @Column(nullable = false)
    private Instant lastUpdatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;









    // ------- contructors -------


    public Request() {
    }

    public Request(String uuid,
                   String cusId,
                   BigDecimal amount,
                   Instant lastUpdatedAt) {
        this.uuid = uuid;
        this.cusId = cusId;
        this.amount = amount;
        this.lastUpdatedAt = lastUpdatedAt;
        this.status = PENDING;
    }













    // -------- getters && setters -----------

    // I intentionally didn't provide some of the setters here.
    // some parameters are supposed to be non-resettable;

    public String getUuid() {
        return uuid;
    }

    public String getCusId() {
        return cusId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public RequestStatus getStatus() {
        return status;
    }


    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }



}
