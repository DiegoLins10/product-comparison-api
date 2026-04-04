package com.example.productcomparison.application.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Double rating,
        String size,
        Double weight,
        String color,
        Map<String, Object> attributes
) {
}
