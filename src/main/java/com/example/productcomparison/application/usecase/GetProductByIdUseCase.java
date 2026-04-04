package com.example.productcomparison.application.usecase;

import com.example.productcomparison.application.dto.ProductDTO;
import com.example.productcomparison.application.mapper.ProductDTOMapper;
import com.example.productcomparison.domain.exception.ProductNotFoundException;
import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.domain.repository.ProductRepository;

import java.util.List;

public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public GetProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(Long id) {
        List<Product> products = productRepository.findByIds(List.of(id));
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado para id: " + id);
        }
        return ProductDTOMapper.toDTO(products.getFirst());
    }
}
