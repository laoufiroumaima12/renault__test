package com.example.garage_test.vehicle.service;

import com.example.garage_test.vehicle.dto.VehicleCreatedEvent;
import com.example.garage_test.vehicle.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleKafkaProducer {

    private final KafkaTemplate<String, VehicleCreatedEvent> kafkaTemplate;

    public void publishVehicleCreated(Vehicle vehicle) {

        VehicleCreatedEvent event =
                new VehicleCreatedEvent(
                        vehicle.getBrand(),
                        vehicle.getFabricationYear(),
                        vehicle.getFuelType(),
                        vehicle.getGarage().getId()
                );
        System.out.println("[KAFKA-PRODUCER] Sending event to Kafka");
        kafkaTemplate.send("vehicle.created", event);

    }


}
