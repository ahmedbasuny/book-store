package com.book.store.system.notification.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class RabbitMQConfiguration {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(rabbitMQProperties.orderEventsExchange());
    }

    @Bean
    Queue newOrdersQueue() {
        return QueueBuilder.durable(rabbitMQProperties.newOrdersQueue()).build();
    }

    @Bean
    Binding newOrdersQueueBinding() {
        return BindingBuilder.bind(newOrdersQueue()).to(exchange()).with(rabbitMQProperties.newOrdersQueue());
    }

    @Bean
    Queue deliveredOrdersQueue() {
        return QueueBuilder.durable(rabbitMQProperties.deliveredOrdersQueue()).build();
    }

    @Bean
    Binding deliveredOrdersQueueBinding() {
        return BindingBuilder.bind(deliveredOrdersQueue())
                .to(exchange())
                .with(rabbitMQProperties.deliveredOrdersQueue());
    }

    @Bean
    Queue cancelledOrdersQueue() {
        return QueueBuilder.durable(rabbitMQProperties.cancelledOrdersQueue()).build();
    }

    @Bean
    Binding cancelledOrdersQueueBinding() {
        return BindingBuilder.bind(cancelledOrdersQueue())
                .to(exchange())
                .with(rabbitMQProperties.cancelledOrdersQueue());
    }

    @Bean
    Queue errorOrdersQueue() {
        return QueueBuilder.durable(rabbitMQProperties.errorOrdersQueue()).build();
    }

    @Bean
    Binding errorOrdersQueueBinding() {
        return BindingBuilder.bind(errorOrdersQueue()).to(exchange()).with(rabbitMQProperties.errorOrdersQueue());
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }
}
