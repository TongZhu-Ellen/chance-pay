package com.tongzhu.chancepay.wallet.inbox;

import com.tongzhu.chancepay.wallet.JsonConverter;

import com.tongzhu.chancepay.wallet.wallet.WalletRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class InboxListener {

    static final String QUEUE_NAME = "chance_queue";

    private final RestTemplate restTemplate= new RestTemplate();

    private final InboxRepository inboxRepository;
    private final WalletRepository walletRepository;

    public InboxListener(InboxRepository inboxRepository, WalletRepository walletRepository) {
        this.inboxRepository = inboxRepository;
        this.walletRepository = walletRepository;
    }



    @RabbitListener(queues = QUEUE_NAME)
    public void inboxListener(String payload) {


        try {
            helpListen(payload);
        } catch (Exception e) {

            // TODO: log.error() about not being able to conduct payment here!

        }





    }



    @Transactional
    public void helpListen(String payload) {
        String uuid = JsonConverter.extractUuid(payload);
        String cusId = JsonConverter.extractCusId(payload);
        BigDecimal amount = JsonConverter.extractAmount(payload);


        boolean existed = inboxRepository.insertIgnore(uuid) == 0;

        if (existed) return;

        boolean deducted = walletRepository.deduct(cusId, amount) == 1;

        inboxRepository.updateStatus(uuid, deducted ? "SUCCEED" : "FAILED");

        try {
            restTemplate.put(
                    "http://orchestrator:8080/internal/requests/" + uuid,
                    deducted);
        } catch (Exception e) {
            // TODO: log.warn() to warn about failed HTTP-req;
        }

    }
}
