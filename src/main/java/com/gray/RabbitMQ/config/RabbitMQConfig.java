package com.gray.RabbitMQ.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${gray.rabbitmq.exchange}")
    String exchange;
    @Value("${gray.rabbitmq.employeeRoutingKey}")
    String employeeRoutingKey;
    @Value("${gray.rabbitmq.companyRoutingKey}")
    String companyRoutingKey;
    @Value("${gray.rabbitmq.companyQueueName}")
    String companyQueueName;
    @Value("${gray.rabbitmq.employeeQueueName}")
    String employeeQueueName;

    @Bean
    Queue employeeQueue() {
        return new Queue(employeeQueueName, true);
    }

    @Bean
    Queue companyQueue() {
        return new Queue(companyQueueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding employeeBinding() {
        return BindingBuilder.bind(employeeQueue()).to(exchange()).with(employeeRoutingKey);
    }

    @Bean
    Binding companyBinding() {
        return BindingBuilder.bind(companyQueue()).to(exchange()).with(companyRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}