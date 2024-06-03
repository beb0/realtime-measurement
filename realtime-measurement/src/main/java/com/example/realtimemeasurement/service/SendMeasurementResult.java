package com.example.realtimemeasurement.service;

import com.example.realtimemeasurement.dto.Avg;
import com.example.realtimemeasurement.dto.MeasurementResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashMap;

@Configuration
@EnableScheduling
public class SendMeasurementResult {

    private final RabbitTemplate rabbitTemplate;
    HashMap<String, Avg> devices = new HashMap<>();
    public SendMeasurementResult(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    //Scheduled to run every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void sendToMQ(){
        //Getting the aggregated averages for each device per address
        this.devices = MeasurementProcessor.getDevices();

        for (HashMap.Entry<String, Avg> entry : devices.entrySet()) {

            String deviceAddress = entry.getKey();
            Avg avgData = entry.getValue();

            MeasurementResult measurementResult = new MeasurementResult();

            //Sending the results through measurementResult class to averagedMeasurement queue
            measurementResult.setDeviceAddress(deviceAddress);
            measurementResult.setAvgHumidity(avgData.getAvgHumidity());
            measurementResult.setAvgTemperature(avgData.getAvgTemperature());
            rabbitTemplate.convertAndSend("averagedMeasurements", measurementResult);
        }

        //Clearing the original hashmap
        devices.clear();
    }
}
