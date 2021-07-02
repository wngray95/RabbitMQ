package com.gray.RabbitMQ.service;
import com.gray.RabbitMQ.model.Company;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gray.RabbitMQ.model.Employee;

@Service
public class RabbitMQSender {

    @Autowired
    AmqpTemplate rabbitTemplate;

    @Value("${gray.rabbitmq.exchange}")
    String exchange;
    @Value("${gray.rabbitmq.employeeRoutingKey}")
    String employeeRoutingKey;
    @Value("${gray.rabbitmq.companyRoutingKey}")
    String companyRoutingKey;


    public void send(Employee employee) {
        rabbitTemplate.convertAndSend(exchange, employeeRoutingKey, employee);
        System.out.println("Send employee = " + employee);
    }

    public void send(Company company) {
        rabbitTemplate.convertAndSend(exchange, companyRoutingKey, company);
        System.out.println("Send company = " + company);
    }
}
