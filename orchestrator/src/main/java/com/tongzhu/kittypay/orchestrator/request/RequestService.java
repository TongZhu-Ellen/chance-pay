package com.tongzhu.kittypay.orchestrator.request;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tongzhu.kittypay.orchestrator.CreateRequestDto;
import com.tongzhu.kittypay.orchestrator.JsonConverter;
import com.tongzhu.kittypay.orchestrator.outbox.Outbox;
import com.tongzhu.kittypay.orchestrator.outbox.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final OutboxRepository outboxRepository;

    public RequestService(RequestRepository requestRepository, OutboxRepository outboxRepository) {
        this.requestRepository = requestRepository;
        this.outboxRepository = outboxRepository;
    }


    @Transactional
    public Request postRequest(CreateRequestDto dto) throws JsonProcessingException {

        String uuid = UUID.randomUUID().toString();
        String cusId = dto.cusId();
        BigDecimal amount = dto.amount();
        Instant curTime = Instant.now();
        


        Request request = new Request(uuid, cusId, amount, curTime);
        Outbox outbox = new Outbox(uuid,
                JsonConverter.toPayload(uuid, cusId, amount),
                curTime);

        request = requestRepository.save(request);
        outbox = outboxRepository.save(outbox);

        return request;

    }
}
