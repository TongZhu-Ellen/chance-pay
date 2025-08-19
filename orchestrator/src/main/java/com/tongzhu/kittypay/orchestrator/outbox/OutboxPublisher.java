package com.tongzhu.kittypay.orchestrator.outbox;




import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service

public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    public OutboxPublisher(OutboxRepository outboxRepository,
                           RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publisher() {

        Outbox outbox = outboxRepository.selectOneToPost();

        if (outbox == null) return;


        outbox.setAcked(false);
        outbox.setReturned(false);
        outbox.setLastTouchedAt(Instant.now());
        outbox.setTryCount(outbox.getTryCount() + 1);

        rabbitTemplate.convertAndSend(
                "",                 // 默认交换机
                "request_queue",        // 队列名 == routingKey
                outbox.getPayload(),            // payload;
                msg -> {
                    msg.getMessageProperties()
                            .setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                },
                new CorrelationData(outbox.getUuid())           // confirm 回调用这个
        );




    }

    @Scheduled(fixedDelay = 1000)
    public void sweeper() {

        outboxRepository.deleteAll(outboxRepository.selectFiveToDelete());
    }



}
