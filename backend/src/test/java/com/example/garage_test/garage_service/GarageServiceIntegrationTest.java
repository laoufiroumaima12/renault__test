package com.example.garage_test.garage_service;

import com.example.garage_test.accessory.dto.AccessoryDto;
import com.example.garage_test.accessory.service.AccessoryService;
import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.dto.PageResponse;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.repository.GarageRepository;
import com.example.garage_test.garage.service.GarageService;
import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.enums.FuelType;
import com.example.garage_test.vehicle.repository.VehicleRepository;
import com.example.garage_test.vehicle.service.VehicleService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class GarageServiceIntegrationTest {
    @Autowired
    private GarageService garageService;
    @Autowired
    private  GarageRepository garageRepository;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private EntityManager entityManager;


    @Test
    void getAllGarages_ShouldReturnPaginatedGarages_WhenGaragesExist() {
        garageService.saveOrUpdate(new GarageRequest("Garage test 1", "123 Main St", "email_test1@gmail.com", "555-1234"), null);
        garageService.saveOrUpdate(new GarageRequest("Garage test 2", "456 Main St", "email_test2@gmail.com", "555-5678"), null);
        PageResponse<GarageDto> response = garageService.getAllGarages(0, 10, "name", Sort.Direction.ASC);
        assertEquals(2, response.content().size());
        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(2, response.totalElements());
        assertEquals(1, response.totalPages());
    }

    @Test
    void saveOrUpdate_ShouldSaveGarage_WhenRequestIsValid() {
        GarageRequest request = new GarageRequest("Garage test", "123 Main St", "email_test@gmail.com","555-1234");
        garageService.saveOrUpdate(request, null);
        List<Garage> garages = garageRepository.findAll();
        assertEquals(1, garages.size());
        Garage savedGarage = garages.get(0);
        assertEquals("Garage test", savedGarage.getName());
        assertEquals("123 Main St", savedGarage.getAddress());
        assertEquals("email_test@gmail.com", savedGarage.getEmail());
        assertEquals("555-1234", savedGarage.getPhoneNumber());
    }

    @Test
    void saveOrUpdate_ShouldUpdateGarage_WhenRequestIsValid() {
        GarageRequest request = new GarageRequest("Garage test", "123 Main St", "email_test@gmail.com","555-1234");
        garageService.saveOrUpdate(request, null);
        GarageRequest requestUpdated = new GarageRequest("Garage test new", "123 Main St new ", "email_test_new@gmail.com","555-1234");
        GarageDto garageUpdated = garageService.saveOrUpdate(requestUpdated, garageRepository.findAll().get(0).getId());
        assertEquals("Garage test new", garageUpdated.getName());
        assertEquals("123 Main St new ", garageUpdated.getAddress());
        assertEquals("email_test_new@gmail.com", garageUpdated.getEmail());
        assertEquals("555-1234", garageUpdated.getPhoneNumber());
    }

    @Test
    void shouldGetGaragesBeVehiclesBrandWithAccessories(){
        AccessoryDto accessoryDto = new AccessoryDto();
        accessoryDto.setName("Accessory test");
        accessoryDto.setDescription("Accessory description");
        accessoryDto.setPrice(100.0);
        GarageDto garage1 = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", "email_test@gmail.com","555-1234"), null);
        GarageDto garage2 = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", "email_test@gmail.com","555-1234"), null);
        VehicleDto vehicleDto1 = vehicleService.addVehicleToGarage(new VehicleRequest("CLIO", 2025, FuelType.DIESEL), garage1.getId());
        VehicleDto vehicleDto2 = vehicleService.addVehicleToGarage(new VehicleRequest("BMW", 2025, FuelType.DIESEL), garage2.getId());
        accessoryService.addAccessoryToVehicle(vehicleDto1.getId(),accessoryDto);
        entityManager.flush();
        List<GarageDto> garagesClio = garageService.getGaragesBeVehiclesBrandWithAccessories("CLIO");
        assertEquals(1, garagesClio.size());
        List<GarageDto> garagesBmw = garageService.getGaragesBeVehiclesBrandWithAccessories("BMW");
        assertEquals(0, garagesBmw.size());
    }

    @Test
    void delete_ShouldDeleteGarage_WhenGarageExists() {
        GarageDto garage = garageService.saveOrUpdate(new GarageRequest("Garage test", "123 Main St", null, null), null);
        VehicleRequest vehicleRequest = new VehicleRequest("CLIO", 2025, FuelType.DIESEL);
        VehicleDto vehicleDto = vehicleService.addVehicleToGarage(vehicleRequest, garage.getId());
        garageService.delete(garage.getId());
        assertTrue(garageRepository.findById(garage.getId()).isEmpty());
        assertTrue(vehicleRepository.findById(vehicleDto.getId()).isEmpty());
    }

}
