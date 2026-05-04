package com.example.garage_test.vehicle.service;

import com.example.garage_test.vehicle.dto.VehicleCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VehicleKafkaConsumer {

    @KafkaListener(topics = "vehicle.created")
    public void consume(VehicleCreatedEvent event) {

        System.out.println(
                "[KAFKA-CONSUMER] Vehicle created → brand="
                        + event.brand()
                        + ", garageId="
                        + event.garageId()
        );
    }

}
