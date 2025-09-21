package com.tongzhu.chancepay.wallet.response;

import com.tongzhu.chancepay.wallet.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ResponseListener {


    private static final String QUEUE_NAME = "chance_queue";

    private static final Logger log = LoggerFactory.getLogger(ResponseListener.class);


    private final RestTemplate restTemplate = new RestTemplate();
    private final ResponseService paymentService;

    public ResponseListener(ResponseService paymentService) {
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


        paymentService.pretendToProcess(uuid, cusId, amount);


    }
}
