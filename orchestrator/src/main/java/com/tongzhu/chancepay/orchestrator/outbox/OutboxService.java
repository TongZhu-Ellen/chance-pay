package com.tongzhu.chancepay.orchestrator.outbox;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }


    @Transactional
    public List<OutboxOutputDTO> selectAndTouch() {

        List<OutboxOutputDTO> dtoLst = new ArrayList<>();

        for (Outbox outbox : outboxRepository.selectFiveToPublish()) {
            outboxRepository.touchOnce(outbox.getUuid());
            dtoLst.add(new OutboxOutputDTO(outbox.getUuid(), outbox.getPayload()));

        }

        return dtoLst;
    }










}
