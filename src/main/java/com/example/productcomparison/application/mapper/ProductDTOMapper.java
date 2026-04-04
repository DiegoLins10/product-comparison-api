package com.example.productcomparison.application.mapper;

import com.example.productcomparison.application.dto.ProductDTO;
import com.example.productcomparison.domain.model.Product;

public final class ProductDTOMapper {

    private ProductDTOMapper() {
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getRating(),
                product.getSize(),
                product.getWeight(),
                product.getColor(),
                product.getAttributes()
        );
    }
}
