package com.tongzhu.chancepay.orchestrator;


import org.springframework.amqp.core.MessageDeliveryMode;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {





    @Bean
    public CachingConnectionFactory setConnectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();

        ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);


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



        return rabbitTemplate;
    }

}
