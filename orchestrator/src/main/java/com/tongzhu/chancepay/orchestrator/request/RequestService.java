package com.tongzhu.chancepay.orchestrator.request;

import com.tongzhu.chancepay.orchestrator.JsonConverter;
import com.tongzhu.chancepay.orchestrator.outbox.Outbox;
import com.tongzhu.chancepay.orchestrator.outbox.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final OutboxRepository outboxRepository;


    public RequestService(RequestRepository requestRepository, OutboxRepository outboxRepository) {
        this.requestRepository = requestRepository;
        this.outboxRepository = outboxRepository;

    }

    @Transactional
    public Request postPaymentRequest(String uuid, RequestInputDTO requestInputDTO) {

        String cusId = requestInputDTO.cusId();
        BigDecimal amount = requestInputDTO.amount();
        Instant instantOfNow = Instant.now();
        String payload = JsonConverter.toPayload(uuid, cusId, amount);

        Request request = new Request(uuid, cusId, amount, instantOfNow);
        Outbox outbox = new Outbox(uuid, payload, instantOfNow);

        request = requestRepository.save(request);
        outbox = outboxRepository.save(outbox);

        

        return request;
    }

    @Transactional
    public int setSucceedOrFailed(String uuid, boolean succeed) {
        if (succeed) return requestRepository.setSucceed(uuid);
        else return requestRepository.setFailed(uuid);
    }




}
