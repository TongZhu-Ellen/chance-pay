package com.tongzhu.chancepay.orchestrator.outbox;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Outbox {




    @Id
    private String uuid;
    private String payload;
    private Instant lastTouchedAt;
    private int tryCount = 0;






    public Outbox() {
    }
    public Outbox(String uuid, String payload, Instant lastTouchedAt) {
        this.uuid = uuid;
        this.payload = payload;
        this.lastTouchedAt = lastTouchedAt;
    }









    public String getPayload() {
        return payload;
    }
    public String getUuid() {
        return uuid;
    }
    public Instant getLastTouchedAt() {
        return lastTouchedAt;
    }
    public int getTryCount() {
        return tryCount;
    }






}
