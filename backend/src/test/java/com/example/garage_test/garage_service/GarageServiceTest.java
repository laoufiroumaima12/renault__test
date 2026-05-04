package com.example.garage_test.garage_service;

import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.dto.PageResponse;
import com.example.garage_test.garage.mapper.GarageMapper;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.repository.GarageRepository;
import com.example.garage_test.garage.service.GarageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GarageServiceTest {

    @InjectMocks
    private GarageService garageService;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private GarageMapper garageMapper;

    private Garage garage1;
    private Garage garage2;
    private GarageDto garageDto2;

    @BeforeEach
    void setUp() {
        garage1 = new Garage();
        garage1.setName("Garage A");
        garage2 = new Garage();
        garage2.setId(2);
        garage2.setName("Garage B");
        garage2.setEmail("email@gmail.com");
        garage2.setPhoneNumber("456788");
        garage2.setAddress("address");
        garageDto2 = new GarageDto();
        garageDto2.setId(2);
        garageDto2.setName("Garage B");
    }

    @Test
    @DisplayName("should get all garages with pagination and sorting")
    public void testGetAllGarages() {

        Page<Garage> pageGarage = new PageImpl<>(List.of(garage1, garage2), PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"), "name"), 2);
        when(garageRepository.findAll(any(Pageable.class))).thenReturn(pageGarage);
        when(garageMapper.toDto(any(Garage.class))).thenReturn(new GarageDto());

        PageResponse<GarageDto> result =  garageService.getAllGarages(0, 10, "name", Sort.Direction.valueOf("ASC"));
        assertEquals(2, result.content().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());
    }

    @Test
    @DisplayName("should get empty garages")
    public void testEmptyGarages() {

        Page<Garage> pageGarage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"), "name"), 0);
        when(garageRepository.findAll(any(Pageable.class))).thenReturn(pageGarage);

        PageResponse<GarageDto> result =  garageService.getAllGarages(0, 10, "name", Sort.Direction.valueOf("ASC"));
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
    }

    @Test
    @DisplayName("should save garage")
    public void testSaveGarage() {
        GarageRequest garageRequest = new GarageRequest("Garage B","address","email@gmail.com","456788");
        when(garageRepository.save(any(Garage.class))).thenReturn(garage2);

        when(garageMapper.toDto(any())).thenReturn(garageDto2);

        GarageDto result = garageService.saveOrUpdate(garageRequest, null);
        verify(garageMapper).updateGarageFromGarageRequest(eq(garageRequest),any(Garage.class));
        assertEquals("Garage B", result.getName());
    }

    @Test
    @DisplayName("should update garage")
    public void testUpdateGarage() {
        garageDto2.setName("Garage B new");
        GarageRequest garageRequest = new GarageRequest("Garage B", "address", "email@gmail.com", "456788");
        when(garageRepository.save(any(Garage.class))).thenReturn(garage2);
        when(garageRepository.findById(any())).thenReturn(java.util.Optional.of(garage2));
        when(garageMapper.toDto(any())).thenReturn(garageDto2);

        GarageDto result = garageService.saveOrUpdate(garageRequest, 2);
        verify(garageMapper).updateGarageFromGarageRequest(eq(garageRequest), any(Garage.class));
        assertEquals("Garage B new", result.getName());
        assertEquals(2, result.getId());
    }

    @Test
    @DisplayName("should delete garage")
    public void testDeleteGarage() {
        garageService.delete(2);
        verify(garageRepository).deleteById(2);
    }
}
