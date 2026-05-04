package com.example.garage_test.accessory.dto;

import lombok.Data;

@Data
public class AccessoryDto {

    private String name;
    private String description;
    private Double price;
    private Integer vehicleId;
}
