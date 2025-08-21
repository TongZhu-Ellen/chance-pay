package com.tongzhu.kittypay.orchestrator.outbox;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutboxScanner {

    private final OutboxRepository outboxRepository;
    private final OutboxPublisher outboxPublisher;




    public OutboxScanner(OutboxRepository outboxRepository, OutboxPublisher outboxPublisher) {
        this.outboxRepository = outboxRepository;
        this.outboxPublisher = outboxPublisher;
    }



    @Scheduled(fixedDelay = 200)
    public void scanEvery200ms() {

        scannerPublish(scannerTouch());

    }

    @Transactional
    public List<OutboxOutputDTO> scannerTouch() {

        List<OutboxOutputDTO> dtoLst = new ArrayList<>();

        for (Outbox outbox : outboxRepository.selectFiveToPublish()) {
            outboxRepository.touchOnce(outbox.getUuid());
            dtoLst.add(new OutboxOutputDTO(outbox.getUuid(), outbox.getPayload()));

        }

        return dtoLst;
    }

    public void scannerPublish(List<OutboxOutputDTO> dtoLst) {
        for (OutboxOutputDTO dto : dtoLst) {
            outboxPublisher.publish(dto);
        }
    }
}
