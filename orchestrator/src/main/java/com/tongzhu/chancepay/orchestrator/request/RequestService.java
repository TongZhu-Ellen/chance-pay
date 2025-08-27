package com.tongzhu.chancepay.orchestrator.request;


import com.tongzhu.chancepay.orchestrator.JsonConverter;
import com.tongzhu.chancepay.orchestrator.outbox.Outbox;
import com.tongzhu.chancepay.orchestrator.outbox.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class RequestService {

    private final OutboxRepository outboxRepository;
    private final RequestRepository requestRepository;

    public RequestService(OutboxRepository outboxRepository, RequestRepository requestRepository) {
        this.outboxRepository = outboxRepository;
        this.requestRepository = requestRepository;
    }












    @Transactional
    public void postRequest(String uuid, RequestInputDto paymentInputDto) {



        String cusId = paymentInputDto.cusId();
        BigDecimal amount = paymentInputDto.amount();
        Instant instantOfNow = Instant.now();
        String payload = JsonConverter.toPayload(uuid, cusId, amount);

        requestRepository.save(new Request(uuid, cusId, amount, instantOfNow));
        outboxRepository.save(new Outbox(uuid, payload, instantOfNow));





    }



    public Optional<Request> getRequest(String uuid) {
        return requestRepository.findById(uuid);
    }



    @Transactional
    public int setSucceedOrFailed(String uuid, boolean succeed) {
        if (succeed) return requestRepository.setSucceed(uuid);
        else return requestRepository.setFailed(uuid);
    }



















}
