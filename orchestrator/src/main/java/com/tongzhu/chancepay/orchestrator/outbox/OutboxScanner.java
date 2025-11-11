package com.tongzhu.chancepay.orchestrator.outbox;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxScanner {



    private final int OUTBOX_PER_SELECT = 5;
    private final int BACKOFF_IN_SECOND = 2;
    private final int INTERVAL_IN_MS = 200;

    private final OutboxService outboxService;
    private final OutboxPublisher outboxPublisher;
    private final JdbcTemplate jdbcTemplate;

    public OutboxScanner(OutboxService outboxService, OutboxPublisher outboxPublisher, JdbcTemplate jdbcTemplate) {
        this.outboxService = outboxService;
        this.outboxPublisher = outboxPublisher;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Scheduled(fixedDelay = INTERVAL_IN_MS)
    public void scanAndPublish() {

        List<Outbox> batch = outboxService.selectTouched(OUTBOX_PER_SELECT, BACKOFF_IN_SECOND);

        for (Outbox outbox : batch) outboxPublisher.publish(outbox);

            /*
            I intentionally didn't do a try-catch here!
            Synchronous exceptions indicate a “big failure” (e.g. broker unavailable) and should bubble up immediately,
            while asynchronous NACKs are “small hiccups” — just log them and keep the outbox for retry.

             */


        int pending = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM outbox", Integer.class);
        System.out.println(pending + " "); // this is on printing num of outboxes!!!






    }




}
