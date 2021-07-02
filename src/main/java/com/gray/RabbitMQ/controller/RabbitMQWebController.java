package com.gray.RabbitMQ.controller;

import com.gray.RabbitMQ.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gray.RabbitMQ.model.Employee;
import com.gray.RabbitMQ.service.RabbitMQSender;

@RestController
@RequestMapping("/gray-rabbitmq/save")
public class RabbitMQWebController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/employee")
    public String employeeProducer(@RequestParam("empName") String empName,
                                   @RequestParam("empId") String empId,
                                   @RequestParam("position") String position) {

        Employee emp = new Employee();
        emp.setEmpId(empId);
        emp.setEmpName(empName);
        emp.setPosition(position);
        rabbitMQSender.send(emp);

        return "Employee submitted for processing";
    }

    @PostMapping(value = "/company")
    public String companyProducer(@RequestParam("companyName") String companyName,
                                  @RequestParam("address") String address) {

        Company company = new Company();
        company.setName(companyName);
        company.setAddress(address);
        rabbitMQSender.send(company);

        return "Company submitted for processing";
    }


}