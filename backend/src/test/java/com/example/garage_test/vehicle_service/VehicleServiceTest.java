package com.example.garage_test.vehicle_service;

import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.repository.GarageRepository;
import com.example.garage_test.garage.utils.GarageValidators;
import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.enums.FuelType;
import com.example.garage_test.vehicle.mapper.VehicleMapper;
import com.example.garage_test.vehicle.model.Vehicle;
import com.example.garage_test.vehicle.repository.VehicleRepository;
import com.example.garage_test.vehicle.service.VehicleKafkaProducer;
import com.example.garage_test.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Named;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleMapper vehicleMapper;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private GarageValidators garageValidators;
    @Mock
    private VehicleKafkaProducer vehicleKafkaProducer;
    private Garage garage;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        garage = new Garage();
        garage.setId(1);
        vehicle = new Vehicle();
        vehicle.setBrand("CLIO");
        vehicleDto = new VehicleDto();
        vehicleDto.setBrand("CLIO");

    }

    @Named("shouldAddVehicleToGarage")
    @Test
    public void shouldAddVehicleToGarage() {
        VehicleRequest vehicleRequest = new VehicleRequest("CLIO",2025, FuelType.DIESEL);
        when(garageRepository.findById(any())).thenReturn(java.util.Optional.of(garage));
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        when(vehicleMapper.toDto(any())).thenReturn(vehicleDto);
        VehicleDto result = vehicleService.addVehicleToGarage(vehicleRequest, 1);
        verify(vehicleMapper).updateVehicleFromVehicleRequest(eq(vehicleRequest), any(Vehicle.class));
        verify(garageValidators).validateGarageCapacity(any(),any());
        assertEquals("CLIO", result.getBrand());
        assertEquals(1,garage.getVehicles().size());
    }

    @Named("shouldUpdateVehicle")
    @Test
    public void shouldUpdateVehicle() {
        vehicleDto.setBrand("BMW");
        VehicleRequest vehicleRequest = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
        when(vehicleRepository.findById(any())).thenReturn(java.util.Optional.of(vehicle));
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        when(vehicleMapper.toDto(any())).thenReturn(vehicleDto);
        VehicleDto result = vehicleService.updateVehicle(vehicleRequest, 1);
        verify(vehicleMapper).updateVehicleFromVehicleRequest(eq(vehicleRequest), any(Vehicle.class));
        assertEquals("BMW", result.getBrand());
    }

    @Named("shouldDeleteVehicle")
    @Test
    public void shouldDeleteVehicle() {
        vehicleService.deleteVehicle(1);
        verify(vehicleRepository).deleteById(1);
    }


    @Named("shouldGetAllVehiclesByGarageId")
    @Test
    public void shouldGetAllVehiclesByGarageId() {
        when(vehicleRepository.findByGarageId(any())).thenReturn(java.util.List.of(vehicle));
        when(vehicleMapper.toDto(any())).thenReturn(vehicleDto);
        var result = vehicleService.getAllVehiclesByGarageId(1);
        assertEquals(1, result.size());
        assertEquals("CLIO", result.get(0).getBrand());
    }

}
