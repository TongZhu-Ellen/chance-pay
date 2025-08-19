package com.tongzhu.kittypay.orchestrator.outbox;


import org.springframework.stereotype.Service;

@Service
public class OutboxService {
    
    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }


    public void markAcked(String uuid) {
        outboxRepository.markAcked(uuid);
    }

    public void markReturned(String uuid) {
        outboxRepository.markReturned(uuid);
    }
}
