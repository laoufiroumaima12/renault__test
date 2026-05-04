package com.example.garage_test.accessory.mapper;

import com.example.garage_test.accessory.dto.AccessoryDto;
import com.example.garage_test.accessory.model.Accessory;
import com.example.garage_test.vehicle.mapper.VehicleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface AccessoryMapper {
    @Mapping(target = "vehicle.id", source = "vehicleId")
    Accessory toModel(AccessoryDto accessoryDto);
    @Mapping(target = "vehicleId", source = "vehicle.id")
    AccessoryDto toDto(Accessory accessory);

    @Mapping(target = "vehicle",ignore = true)
    @Mapping(target = "id",ignore = true)
    void updateAccessoryFromDto(AccessoryDto dto, @MappingTarget Accessory accessory);
}
