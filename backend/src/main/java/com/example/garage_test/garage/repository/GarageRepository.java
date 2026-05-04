package com.example.garage_test.garage.repository;

import com.example.garage_test.garage.model.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Integer> {

    @EntityGraph(attributePaths = "vehicles")
    Page<Garage> findAll(Pageable pageable);

    List<Garage> findByVehiclesBrand(String brand);

}
