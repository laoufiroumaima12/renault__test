package com.example.garage_test.vehicle.service;

import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.repository.GarageRepository;
import com.example.garage_test.garage.service.GarageService;
import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.mapper.VehicleMapper;
import com.example.garage_test.vehicle.model.Vehicle;
import com.example.garage_test.vehicle.repository.VehicleRepository;
import com.example.garage_test.garage.utils.GarageValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final GarageRepository garageRepository;
    private final GarageValidators garageValidators;
    private final VehicleKafkaProducer vehicleKafkaProducer;

    public VehicleDto addVehicleToGarage(VehicleRequest request, Integer garageId) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new IllegalArgumentException("Garage with id " + garageId + " does not exist"));
        garageValidators.validateGarageCapacity(garage.getVehicles(), garage);
        Vehicle vehicle = new Vehicle();
        vehicleMapper.updateVehicleFromVehicleRequest(request, vehicle);
        vehicle.setGarage(garage);
        garage.addVehicle(vehicle);
        vehicleKafkaProducer.publishVehicleCreated(vehicle);
        return vehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    public VehicleDto updateVehicle(VehicleRequest request, Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + vehicleId + " does not exist"));
        vehicleMapper.updateVehicleFromVehicleRequest(request,vehicle);
        return vehicleMapper.toDto(vehicleRepository.save(vehicle));
    }


    public void deleteVehicle(Integer vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }

    public List<VehicleDto> getAllVehiclesByGarageId(Integer garageId) {
        return vehicleRepository.findByGarageId(garageId).stream()
                .map(vehicleMapper::toDto)
                .toList();
    }

    public List<VehicleDto> getAllVehiclesByBrand(String brand) {
        return vehicleRepository.findByBrand(brand).stream()
                .map(vehicleMapper::toDto)
                .toList();
    }


}
