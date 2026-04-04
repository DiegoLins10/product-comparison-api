package com.example.productcomparison.infrastructure.persistence.mapper;

import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.infrastructure.persistence.entity.ProductJpaEntity;

import java.util.HashMap;
import java.util.Map;

public final class ProductPersistenceMapper {

    private ProductPersistenceMapper() {
    }

    public static Product toDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImageUrl(),
                entity.getRating(),
                entity.getSize(),
                entity.getWeight(),
                entity.getColor(),
                new HashMap<>(entity.getAttributes())
        );
    }

    public static ProductJpaEntity toJpa(Product domain) {
        Map<String, String> attributes = new HashMap<>();
        domain.getAttributes().forEach((key, value) -> attributes.put(key, value == null ? null : String.valueOf(value)));

        return new ProductJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice(),
                domain.getImageUrl(),
                domain.getRating(),
                domain.getSize(),
                domain.getWeight(),
                domain.getColor(),
                attributes
        );
    }
}
