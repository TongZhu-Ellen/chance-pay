package com.tongzhu.kittypay.orchestrator;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    public static final String QUEUE_NAME = "request_queue";

    @Bean
    public Queue setQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public CachingConnectionFactory setConnectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();

        ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        ccf.setPublisherReturns(true);
        ccf.setChannelCacheSize(1);

        return ccf;



    }
}
