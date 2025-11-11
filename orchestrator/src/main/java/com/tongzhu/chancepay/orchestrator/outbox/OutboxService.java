package com.tongzhu.chancepay.orchestrator.outbox;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OutboxService {

    private final OutboxRepository outboxRepository;


    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }





    @Transactional
    public List<Outbox> selectTouched(int outboxPerSelect, int backoffInSecond) {
        List<Outbox> batch = outboxRepository.selectBatch(outboxPerSelect, backoffInSecond);



        outboxRepository.touchBatch(batch.stream().map(Outbox::getUuid).toList());

        return batch;

    }
}
