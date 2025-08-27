package com.tongzhu.chancepay.wallet.payment;

import com.tongzhu.chancepay.wallet.JsonConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class PaymentListener {


    static final String QUEUE_NAME = "chance_queue";





    private final RestTemplate restTemplate = new RestTemplate();
    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }












    @RabbitListener(queues = QUEUE_NAME)
    public void inboxListener(String payload) {

        // try-catch block makes sure of ACK,
        // since we don't have DLQ yet, so we can't afford NACK of poisonous msgs.


        try {
            String uuid = JsonConverter.extractUuid(payload);
            String cusId = JsonConverter.extractCusId(payload);
            BigDecimal amount = JsonConverter.extractAmount(payload);
            boolean deducted = paymentService.process(uuid, cusId, amount);

            try {
                restTemplate.put(
                        "http://localhost:8080/internal/requests/" + uuid,
                        deducted);
            } catch (Exception e) {
                // TODO: log.warn() to warn about failed HTTP-req;
            }


        } catch (Exception e) {

            // TODO: log.error() about not being able to conduct payment here!

        }





    }
}
