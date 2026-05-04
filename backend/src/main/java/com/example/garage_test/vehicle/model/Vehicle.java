package com.example.garage_test.vehicle.model;

import com.example.garage_test.vehicle.enums.FuelType;
import com.example.garage_test.accessory.model.Accessory;
import com.example.garage_test.garage.model.Garage;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name = "vehicle", schema = "work")

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private Integer fabricationYear;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Accessory> accessories = new ArrayList<>();

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
        accessory.setVehicle(this);
    }

}