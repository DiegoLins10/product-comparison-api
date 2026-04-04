package com.example.productcomparison.domain.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Product {

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String imageUrl;
    private final Double rating;
    private final String size;
    private final Double weight;
    private final String color;
    private final Map<String, Object> attributes;

    public Product(
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
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.size = size;
        this.weight = weight;
        this.color = color;
        this.attributes = attributes == null ? new HashMap<>() : new HashMap<>(attributes);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public String getSize() {
        return size;
    }

    public Double getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<>(attributes);
    }
}
