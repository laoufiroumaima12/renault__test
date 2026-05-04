package com.example.garage_test.vehicle.repository;

import com.example.garage_test.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
        List<Vehicle> findByBrand(String brand);
        List<Vehicle> findByGarageId(Integer garageId);
}
