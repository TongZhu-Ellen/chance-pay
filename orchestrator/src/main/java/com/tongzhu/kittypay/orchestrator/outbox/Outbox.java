package com.tongzhu.kittypay.orchestrator.outbox;

import jakarta.persistence.*;


import java.time.Instant;

@Entity
public class Outbox {



    @Id
    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private Instant lastTouchedAt;



    @Column(nullable = false)
    private boolean acked;

    @Column(nullable = false)
    private boolean returned;







// --------- Constructors ---------


    public Outbox() {
    }


    public Outbox(String uuid, String payload, Instant lastTouchedAt) {
        this.uuid = uuid;
        this.payload = payload;
        this.lastTouchedAt = lastTouchedAt;

        this.acked = false;
        this.returned = false;

    }






    // --------- getter && setter ---------



    public String getUuid() {
        return uuid;
    }

    public String getPayload() {
        return payload;
    }



    public Instant getLastTouchedAt() {
        return lastTouchedAt;
    }

    public void setLastTouchedAt(Instant lastTouchedAt) {
        this.lastTouchedAt = lastTouchedAt;
    }



    public boolean isAcked() {
        return acked;
    }

    public void setAcked(boolean acked) {
        this.acked = acked;
    }



    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    










}







