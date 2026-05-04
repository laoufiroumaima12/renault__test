package com.example.garage_test.accessory.model;

import com.example.garage_test.vehicle.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accessory", schema = "work")
@Data
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
