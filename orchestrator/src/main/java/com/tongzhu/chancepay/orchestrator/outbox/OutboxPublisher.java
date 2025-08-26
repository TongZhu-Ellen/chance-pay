package com.tongzhu.chancepay.orchestrator.outbox;



import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OutboxPublisher {

    public static final String QUEUE_NAME = "chance_queue";





    private final RabbitTemplate rabbitTemplate;
    private final OutboxRepository outboxRepository;




    public OutboxPublisher(RabbitTemplate rabbitTemplate, OutboxRepository outboxRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.outboxRepository = outboxRepository;

    }




    public void publish(OutboxOutputDTO outboxOutputDTO) {

        String uuid = outboxOutputDTO.uuid();
        String payload = outboxOutputDTO.payload();



        CorrelationData cd = new CorrelationData(uuid);

        rabbitTemplate.convertAndSend("", QUEUE_NAME, payload, cd);

        cd.getFuture().whenComplete((confirm, ex) -> {



            if (ex == null && confirm.isAck()) {
                outboxRepository.deleteById(uuid);
            } else {
                // TODO: logging;
            }
        });




    }











}
