package com.example.realtimemeasurement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class RealtimeMeasurementApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RealtimeMeasurementApplication.class).run(args);
    }

}
