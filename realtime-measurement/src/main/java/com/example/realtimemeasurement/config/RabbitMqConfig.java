package com.example.realtimemeasurement.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue averagedMeasurementsQueue() {
        return new Queue("averagedMeasurements");
    }

    @Bean
    public Queue rawMeasurementsQueue() {
        return new Queue("rawMeasurements");
    }

    @Bean
    @Primary
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
