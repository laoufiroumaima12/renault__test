package com.example.garage_test.garage.service;


import com.example.garage_test.garage.dto.GarageDto;
import com.example.garage_test.garage.dto.GarageRequest;
import com.example.garage_test.garage.dto.PageResponse;
import com.example.garage_test.garage.mapper.GarageMapper;
import com.example.garage_test.garage.model.Garage;
import com.example.garage_test.garage.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class GarageService {
    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    @Transactional(readOnly = true)
    public PageResponse<GarageDto> getAllGarages(int page, int size, String sortBy, Sort.Direction sortDirection){
        Pageable pageable = PageRequest.of(page, size,sortDirection,sortBy);

        Page<GarageDto> pageGarage =  garageRepository.findAll(pageable).map(garageMapper::toDto);
        return new PageResponse<>(pageGarage.getContent(), pageGarage.getNumber(), pageGarage.getSize(), pageGarage.getTotalElements(), pageGarage.getTotalPages());

    }

    public GarageDto saveOrUpdate(GarageRequest request, Integer id){

        Garage garage = (id == null) ? new Garage() : getGarage(id);

        garageMapper.updateGarageFromGarageRequest(request, garage);

        return garageMapper.toDto(garageRepository.save(garage));
    }

    public GarageDto getGarageById(Integer id){
        return garageMapper.toDto(getGarage(id));
    }

    public void delete(Integer id){
        garageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Garage getGarage(Integer id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The garage with the given id does not exist"));
    }

    @Transactional(readOnly = true)
    public List<GarageDto> getGaragesBeVehiclesBrandWithAccessories(String brand){
        return garageRepository.findByVehiclesBrand(brand).stream()
                .filter(garage -> garage.getVehicles().stream()
                        .anyMatch(vehicle -> !CollectionUtils.isEmpty(vehicle.getAccessories())))
                .map(garageMapper::toDto)
                .toList();
    }




}
