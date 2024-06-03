package com.example.realtimemeasurement.controller;

import com.example.realtimemeasurement.dto.BleMeasurement;
import com.example.realtimemeasurement.dto.MeasurementResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class MeasurementsController {

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public MeasurementsController(RabbitTemplate rabbitTemplate, SimpMessagingTemplate simpMessagingTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/measurements")
    public void getRawMeasurements(BleMeasurement measurement) {
        rabbitTemplate.convertAndSend("rawMeasurements", measurement);

    }

    @RabbitListener(queues = {"averagedMeasurements"})
    public void sendMeasurementResults(MeasurementResult measurementResult) {
        simpMessagingTemplate.convertAndSend("/topic/test", measurementResult);
    }

}
