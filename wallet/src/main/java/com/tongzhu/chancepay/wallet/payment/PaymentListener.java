package com.tongzhu.chancepay.wallet.payment;

import com.tongzhu.chancepay.wallet.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class PaymentListener {


    private static final String QUEUE_NAME = "chance_queue";

    private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);

    private static final String CALLBACK_URL = "http://localhost:8080/internal/requests/";





    private final RestTemplate restTemplate = new RestTemplate();
    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }












    @RabbitListener(queues = QUEUE_NAME)
    public void inboxListener(String payload) {

        // try-catch block makes sure of ACK,
        // since we don't have DLQ yet, so we can't afford NACK of poisonous msgs.
        String uuid = null;
        String cusId = null;
        BigDecimal amount = null;
        Boolean deducted = null;


        try {
            uuid = JsonConverter.extractUuid(payload);
            cusId = JsonConverter.extractCusId(payload);
            amount = JsonConverter.extractAmount(payload);

        } catch (Exception payloadReadingExp) {
            log.error("[PAYLOAD_CORRUPTION_ALERT] | payload: {}", payload, payloadReadingExp);
            return;
        }


        deducted = paymentService.pretendToProcess(uuid, cusId, amount);
        if (deducted == null) return; // no need to callback!






        try {
            restTemplate.put(
                    CALLBACK_URL + uuid,
                    deducted);
        } catch (Exception callbackSendingExp) {
            log.warn("[FAILED TO CALLBACK FROM WALLET] | url: {}, uuid: {}, cusId: {}, amount: {}, deducted: {}",
                    CALLBACK_URL + uuid, uuid, cusId, amount, deducted,
                    callbackSendingExp);
        }













    }
}
