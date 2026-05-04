package com.example.garage_test.vehicle.dto;

import com.example.garage_test.vehicle.enums.FuelType;

public record VehicleRequest(String brand, Integer fabricationYear, FuelType fuelType) {
}
