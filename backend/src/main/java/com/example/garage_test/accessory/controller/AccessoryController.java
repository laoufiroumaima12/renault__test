package com.example.garage_test.accessory.controller;

import com.example.garage_test.accessory.dto.AccessoryDto;
import com.example.garage_test.accessory.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accessory")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @PostMapping
    public ResponseEntity<?> addAccessory(@RequestBody AccessoryDto accessoryDto,
                                        @RequestParam Integer vehicleId) {
        try {
            return ResponseEntity.ok(accessoryService.addAccessoryToVehicle(vehicleId, accessoryDto));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping({"/{accessoryId}"})
    public ResponseEntity<?> updateAccessory(@RequestBody AccessoryDto accessoryDto,
                                           @PathVariable Integer accessoryId) {
        try {
            return ResponseEntity.ok(accessoryService.updateAccessory(accessoryId, accessoryDto));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccessory(@RequestParam Integer accessoryId) {
        try {
            accessoryService.deleteAccessoryFromVehicle(accessoryId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public List<AccessoryDto> getAccessoriesByVehicleId(@PathVariable Integer vehicleId) {
        return accessoryService.getAccessoriesByVehicleId(vehicleId);
    }




}
