package com.example.garage_test.vehicle_service;

import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.service.GarageService;
import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.enums.FuelType;
import com.example.garage_test.vehicle.mapper.VehicleMapper;
import com.example.garage_test.vehicle.repository.VehicleRepository;
import com.example.garage_test.vehicle.service.VehicleService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class VehicleServiceIntegrationTest {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private GarageService garageService;
    @Autowired
    EntityManager entityManager;



    @Test
    void shouldAddVehicleToGarage() {
        GarageDto garageDto = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", null, null), null);
        VehicleRequest vehicleRequest = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
        VehicleDto vehicleDto = vehicleService.addVehicleToGarage(vehicleRequest, garageDto.getId());
        assertEquals("CLIO", vehicleDto.getBrand());
        assertEquals(2025, vehicleDto.getFabricationYear());
        assertEquals(FuelType.DIESEL, vehicleDto.getFuelType());
        Garage garage = garageService.getGarage(garageDto.getId());
        assertEquals(1, garage.getVehicles().size());
    }

    @Test
    void shouldThrowExceptionWhenGarageIsFull() {
        GarageDto garageDto = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", null, null), null);

        for(int i=0; i<50; i++){
            VehicleRequest vehicleRequest = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
            vehicleService.addVehicleToGarage(vehicleRequest, garageDto.getId());
        }
        Garage garage = garageService.getGarage(garageDto.getId());
        assertEquals(50, garage.getVehicles().size());
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehicleService.addVehicleToGarage
                        (new VehicleRequest("BMW", 2025, FuelType.DIESEL), garageDto.getId())
        );
        assertEquals("The garage Garage test is already full", exception.getMessage());
    }


    @Test
    void shouldGetVehicleByCriteria() {
        GarageDto garageDto = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", null, null), null);
        VehicleRequest vehicleRequest1 = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
        vehicleService.addVehicleToGarage(vehicleRequest1, garageDto.getId());
        VehicleRequest vehicleRequest2 = new VehicleRequest("BMW", 2025, FuelType.DIESEL);
        vehicleService.addVehicleToGarage(vehicleRequest2, garageDto.getId());
        List<VehicleDto> vehiclesByGarage = vehicleService.getAllVehiclesByGarageId(garageDto.getId());
        assertEquals(2, vehiclesByGarage.size());
        assertEquals(1, vehicleService.getAllVehiclesByBrand("CLIO").size());
        assertEquals(0, vehicleService.getAllVehiclesByBrand("RENAULT").size());

    }

    @Test
    void shouldDeleteVehicle() {
        GarageDto garageDto = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", null, null), null);
        VehicleRequest vehicleRequest = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
        VehicleDto vehicleDto = vehicleService.addVehicleToGarage(vehicleRequest, garageDto.getId());
        vehicleService.deleteVehicle(vehicleDto.getId());
        assertTrue(vehicleRepository.findById(vehicleDto.getId()).isEmpty());
    }

}


