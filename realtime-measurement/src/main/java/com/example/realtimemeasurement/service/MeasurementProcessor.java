package com.example.realtimemeasurement.service;

import com.example.realtimemeasurement.dto.Avg;
import com.example.realtimemeasurement.dto.BleMeasurement;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class MeasurementProcessor {
    private final RabbitTemplate rabbitTemplate;

    //creating a map with BleMeasurement.Measurement.Data.deviceAddress as key and Avg class as value
    static HashMap<String, Avg> devices = new HashMap<>();

    //So that it can be referenced in SendMeasurementResult
    public static HashMap<String, Avg> getDevices() {
        return devices;
    }

    public MeasurementProcessor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //Listening to queue "rawMeasurements" and binding it to class BleMeasurement
    @RabbitListener(queues = {"rawMeasurements"})
    public void calculateAverage(BleMeasurement measurement){
        //Handling empty Object
        if(measurement.getMeasurements() == null){
            return;
        }
        //looping through array of measurements for each object received
        for(BleMeasurement.Measurement measurementObject : measurement.getMeasurements()){
            //Updating Avg if key already exists
            if(devices.containsKey(measurementObject.getDeviceAddress())){

                Avg newAvg = new Avg();

                newAvg = devices.get(measurementObject.getDeviceAddress());

                newAvg.setReadingCount(newAvg.getReadingCount() + 1);
                newAvg.setSumTemperature(newAvg.getSumTemperature() + measurementObject.getData().getTemperature());
                newAvg.setAvgTemperature(newAvg.getSumTemperature()/ newAvg.getReadingCount());
                newAvg.setSumHumidity(newAvg.getSumHumidity() + measurementObject.getData().getHumidity());
                newAvg.setAvgHumidity(newAvg.getSumHumidity()/ newAvg.getReadingCount());

                devices.put(measurementObject.getDeviceAddress(), newAvg);
            }
            //appending new entry if the key doesn't exist
            else {
                Avg newDevice = new Avg();

                newDevice.setSumTemperature(measurementObject.getData().getTemperature());
                newDevice.setSumHumidity(measurementObject.getData().getHumidity());
                newDevice.setAvgTemperature(measurementObject.getData().getTemperature());
                newDevice.setAvgHumidity(measurementObject.getData().getHumidity());
                newDevice.setReadingCount(1);

                devices.put(measurementObject.getDeviceAddress(), newDevice);
            }
        }
    }
}
