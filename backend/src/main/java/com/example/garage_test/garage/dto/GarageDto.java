package com.example.garage_test.garage.dto;

import com.example.garage_test.vehicle.dto.VehicleDto;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GarageDto {
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private List<VehicleDto> vehicles = new ArrayList<>();
    private Map<DayOfWeek,OpeningTimeRecord> openingHours = new HashMap<>();
}
