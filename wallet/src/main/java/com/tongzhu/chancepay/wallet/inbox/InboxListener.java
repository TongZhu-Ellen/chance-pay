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

    private final InboxService inboxService;

    public InboxListener(InboxService inboxService) {
        this.inboxService = inboxService;
    }


    @RabbitListener(queues = QUEUE_NAME)
    public void inboxListener(String payload) {


        try {
            inboxService.process(payload);
        } catch (Exception e) {

            // TODO: log.error() about not being able to conduct payment here!

        }





    }




}
