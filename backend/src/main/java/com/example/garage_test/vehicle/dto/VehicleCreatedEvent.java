package com.example.garage_test.vehicle.dto;

import com.example.garage_test.vehicle.enums.FuelType;

public record VehicleCreatedEvent(
        String brand,
        Integer fabricationYear,
        FuelType fuelType,
        Integer garageId
) { }
