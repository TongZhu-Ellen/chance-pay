package com.tongzhu.kittypay.orchestrator;


import com.tongzhu.kittypay.orchestrator.outbox.OutboxService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;


@Configuration
public class RabbitConfig {

    private final OutboxService outboxService;

    public RabbitConfig(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @Bean
    public Queue payloadQueue() {

        return new Queue("payload_queue", true); // true = durable
    }



    @Bean
    public RabbitTemplate myRabbitTemplate(ConnectionFactory cf) {

        RabbitTemplate temp = new RabbitTemplate(cf);

        // --- Confirm 回调：到交换机 ---
        temp.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                outboxService.markAcked(correlationData.getId());
            }
        });

        // --- Return 回调：到不了队列 ---
        temp.setReturnsCallback(returned -> {
            byte[] body = returned.getMessage().getBody();        // payload
            String uuid = JsonConverter.extractUuid(new String(body, StandardCharsets.UTF_8));
            outboxService.markReturned(uuid);

        });

        // 开启 mandatory：不可路由时才会触发上面的 Return 回调
        temp.setMandatory(true);

        return temp;
    }

}
