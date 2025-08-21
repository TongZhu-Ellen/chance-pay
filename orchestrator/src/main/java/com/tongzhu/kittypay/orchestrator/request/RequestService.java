package com.tongzhu.kittypay.orchestrator.request;

import com.tongzhu.kittypay.orchestrator.JsonConverter;
import com.tongzhu.kittypay.orchestrator.outbox.Outbox;
import com.tongzhu.kittypay.orchestrator.outbox.OutboxOutputDTO;
import com.tongzhu.kittypay.orchestrator.outbox.OutboxPublisher;
import com.tongzhu.kittypay.orchestrator.outbox.OutboxRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final OutboxRepository outboxRepository;
    private final OutboxPublisher outboxPublisher;

    public RequestService(RequestRepository requestRepository, OutboxRepository outboxRepository, OutboxPublisher outboxPublisher) {
        this.requestRepository = requestRepository;
        this.outboxRepository = outboxRepository;
        this.outboxPublisher = outboxPublisher;
    }

    public Request postPaymentRequest(String uuid, RequestInputDTO requestInputDTO) {

        String cusId = requestInputDTO.cusId();
        BigDecimal amount = requestInputDTO.amount();
        Instant instantOfNow = Instant.now();
        String payload = JsonConverter.toPayload(uuid, cusId, amount);

        Request request = new Request(uuid, cusId, amount, instantOfNow);
        Outbox outbox = new Outbox(uuid, payload, instantOfNow);

        request = requestRepository.save(request);
        outbox = outboxRepository.save(outbox);

        outboxRepository.touchOnce(uuid);
        outboxPublisher.publish(new OutboxOutputDTO(uuid, payload));

        return request;
    }
}
