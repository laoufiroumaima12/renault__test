package com.example.garage_test.accessory.service;

import com.example.garage_test.accessory.dto.AccessoryDto;
import com.example.garage_test.accessory.mapper.AccessoryMapper;
import com.example.garage_test.accessory.model.Accessory;
import com.example.garage_test.accessory.repository.AccessoryRepository;
import com.example.garage_test.vehicle.model.Vehicle;
import com.example.garage_test.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AccessoryService {
    private final AccessoryRepository accessoryRepository;
    private final AccessoryMapper accessoryMapper;
    private final VehicleRepository vehicleRepository;

    public AccessoryDto addAccessoryToVehicle(Integer vehicleId, AccessoryDto accessoryDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("The vehicle does not exist"));
        Accessory accessory = new Accessory();
        accessoryMapper.updateAccessoryFromDto(accessoryDto, accessory);
        accessory.setVehicle(vehicle);
        vehicle.addAccessory(accessory);
        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    public AccessoryDto updateAccessory(Integer accessoryId, AccessoryDto accessoryDto) {
        Accessory accessory = accessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new IllegalArgumentException("The accessory does not exist"));
        accessoryMapper.updateAccessoryFromDto(accessoryDto, accessory);

        return accessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    public void deleteAccessoryFromVehicle(Integer accessoryId) {
        accessoryRepository.deleteById(accessoryId);
    }

    @Transactional(readOnly = true)
    public List<AccessoryDto> getAccessoriesByVehicleId(Integer vehicleId) {
        return accessoryRepository.findByVehicleId(vehicleId).stream()
                .map(accessoryMapper::toDto)
                .toList();
    }


}
