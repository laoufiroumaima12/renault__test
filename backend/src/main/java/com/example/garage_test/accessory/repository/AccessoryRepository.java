package com.example.garage_test.accessory.repository;

import com.example.garage_test.accessory.model.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Integer> {
    List<Accessory> findByVehicleId(Integer vehicleId);
}
