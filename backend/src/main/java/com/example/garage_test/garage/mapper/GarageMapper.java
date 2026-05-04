package com.example.garage_test.garage.mapper;


import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.dto.OpeningTimeRecord;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.model.OpeningTime;
import com.example.garage_test.vehicle.mapper.VehicleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface GarageMapper {
    @Mapping(source = "openingTimes", target = "openingHours", qualifiedByName = "toOpeningHours")
    GarageDto toDto(Garage garage);

    @Mapping(target = "openingTimes",ignore = true)
    Garage toModel(GarageDto garageDto);


    @Mapping(target = "vehicles",ignore = true)
    @Mapping(target = "openingTimes",ignore = true)
    @Mapping(target = "id",ignore = true)
    void updateGarageFromGarageRequest(GarageRequest request, @MappingTarget Garage garageToUpdate);

    @Named("toOpeningHours")
    default Map<DayOfWeek, OpeningTimeRecord> toOpeningHours(List<OpeningTime> openingTimeList) {
        Map<DayOfWeek, OpeningTimeRecord> openingTimeMap = new HashMap<>();
        openingTimeList.forEach(openingTime -> {
            openingTimeMap.put(openingTime.getDayOfWeek(), new OpeningTimeRecord(openingTime.getStartTime(), openingTime.getEndTime()));

        });
        return openingTimeMap;
    }
}
