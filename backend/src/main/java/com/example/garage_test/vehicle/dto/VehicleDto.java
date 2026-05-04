package com.example.garage_test.vehicle.dto;

import com.example.garage_test.accessory.dto.AccessoryDto;
import com.example.garage_test.vehicle.enums.FuelType;
import lombok.Data;

import java.util.List;

@Data
public class VehicleDto {
    private Integer id;
    private String brand;
    private Integer fabricationYear;
    private FuelType fuelType;
    private Integer garageId;
    private List<AccessoryDto> accessories;
}
