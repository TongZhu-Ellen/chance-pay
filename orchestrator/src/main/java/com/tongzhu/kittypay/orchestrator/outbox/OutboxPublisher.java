package com.tongzhu.kittypay.orchestrator.outbox;

import com.tongzhu.kittypay.orchestrator.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


@Component
@DependsOn("rabbitAdmin")
public class OutboxPublisher {

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

        rabbitTemplate.convertAndSend("", RabbitConfig.QUEUE_NAME, payload, cd);

        cd.getFuture().whenComplete((confirm, ex) -> {


            var returned = cd.getReturned();
            if (ex == null && returned == null && confirm.isAck()) {
                outboxRepository.deleteById(uuid);
            } else {
                // TODO: logging;
            }
        });




    }






}
