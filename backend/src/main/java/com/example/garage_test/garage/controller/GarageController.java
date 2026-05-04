package com.example.garage_test.garage.controller;


import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.dto.PageResponse;
import com.example.garage_test.garage.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/garage")
@RequiredArgsConstructor
public class GarageController {
    private final GarageService garageService;

    @GetMapping("/all")
    public PageResponse<GarageDto> getAllGarages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection) {
        return garageService.getAllGarages(page, size, sortBy, sortDirection);
    }

    @GetMapping("/{id}")
    public GarageDto getGarageById(@PathVariable Integer id) {
        return garageService.getGarageById(id);
    }

    @PostMapping
    public ResponseEntity<?> addGarage(@RequestBody GarageRequest request) {
        try {
            return ResponseEntity.ok(garageService.saveOrUpdate(request,null));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateGarage(@RequestBody GarageRequest request, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(garageService.saveOrUpdate(request, id));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGarage(@PathVariable Integer id) {
        try {
            garageService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


    @GetMapping("/brand")
    public List<GarageDto> getGaragesByBrand(@RequestParam String brand) {
        return garageService.getGaragesBeVehiclesBrandWithAccessories(brand);
    }


}
