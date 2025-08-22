package com.tongzhu.kittypay.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    public static final String QUEUE_NAME = "kitty_queue";

    @Bean
    public Queue setQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public CachingConnectionFactory setConnectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();

        ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        ccf.setPublisherReturns(true);
        ccf.setChannelCacheSize(1);

        return ccf;



    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 【核心配置】设置全局默认消息交付模式为 PERSISTENT (持久化)
        rabbitTemplate.setBeforePublishPostProcessors(message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

        rabbitTemplate.setMandatory(true);

        return rabbitTemplate;
    }

}
