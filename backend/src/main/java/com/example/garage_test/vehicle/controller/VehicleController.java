package com.example.garage_test.vehicle.controller;

import com.example.garage_test.vehicle.dto.VehicleDto;
import com.example.garage_test.vehicle.dto.VehicleRequest;
import com.example.garage_test.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService service;

    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody VehicleRequest request,
                                     @RequestParam Integer garageId) {
        try {
            return ResponseEntity.ok(service.addVehicleToGarage(request, garageId));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }

    }

    @PutMapping({"/{vehicleId}"})
    public ResponseEntity<?> updateVehicle(@RequestBody VehicleRequest request,
                                           @PathVariable Integer vehicleId) {
        try {
            return ResponseEntity.ok(service.updateVehicle(request, vehicleId));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Integer vehicleId) {
        try {
            service.deleteVehicle(vehicleId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/by-garage/{garageId}")
    public List<VehicleDto> getVehiclesByGarage(@PathVariable Integer garageId) {
        return  service.getAllVehiclesByGarageId(garageId);
    }

    @GetMapping("/by-brand")
    public List<VehicleDto> getVehiclesByBrand(@RequestParam String brand) {
        return service.getAllVehiclesByBrand(brand);
    }

}
