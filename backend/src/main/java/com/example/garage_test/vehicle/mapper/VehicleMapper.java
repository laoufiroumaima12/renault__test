package com.example.garage_test.vehicle.mapper;

import com.example.garage_test.accessory.mapper.AccessoryMapper;
import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AccessoryMapper.class})
public interface VehicleMapper {

    @Mapping(target = "garage.id", source = "garageId")
    Vehicle toModel(VehicleDto dto);

    @Mapping(target = "garageId", source = "garage.id")
    VehicleDto toDto(Vehicle model);

    @Mapping(target = "accessories",ignore = true)
    @Mapping(target = "garage",ignore = true)
    @Mapping(target = "id",ignore = true)
    void updateVehicleFromVehicleRequest(VehicleRequest request, @MappingTarget Vehicle vehicle);

}
