package com.tongzhu.chancepay.orchestrator.outbox;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutboxScanner {

    private final OutboxService outboxService;
    private final OutboxPublisher outboxPublisher;

    public OutboxScanner(OutboxService outboxService, OutboxPublisher outboxPublisher) {
        this.outboxService = outboxService;
        this.outboxPublisher = outboxPublisher;
    }


    @Scheduled(fixedDelay = 200)
    public void scanEvery2000ms() {

        List<OutboxOutputDTO> dtoLst = outboxService.selectAndTouch();

        for (OutboxOutputDTO dto : dtoLst) {
            outboxPublisher.publish(dto);
        }

    }




}
