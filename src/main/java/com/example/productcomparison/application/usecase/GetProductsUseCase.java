package com.example.productcomparison.application.usecase;

import com.example.productcomparison.application.dto.ProductDTO;
import com.example.productcomparison.application.mapper.ProductDTOMapper;
import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.domain.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

public class GetProductsUseCase {

    private final ProductRepository productRepository;

    public GetProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute(List<Long> ids) {
        List<Product> products = (ids == null || ids.isEmpty())
                ? productRepository.findAll()
                : productRepository.findByIds(ids);

        return Collections.unmodifiableList(products.stream().map(ProductDTOMapper::toDTO).toList());
    }
}
