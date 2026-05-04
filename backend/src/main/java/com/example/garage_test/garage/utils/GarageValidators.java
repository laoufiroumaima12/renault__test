package com.example.garage_test.garage.utils;

import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.vehicle.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GarageValidators {
    private final static int MAX_VEHICLES = 50;

    public void validateGarageCapacity(List<Vehicle> vehicles, Garage garage){

        if(vehicles != null && vehicles.size() == MAX_VEHICLES){
            throw new IllegalArgumentException("The garage "+ garage.getName()+" is already full");
        };
    }
}
