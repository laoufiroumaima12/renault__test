package com.example.garage_test.garage.dto;

import org.springframework.data.domain.Sort;

public record PageRequest(int page, int size, String sortBy, Sort.Direction sortDirection) {
}
