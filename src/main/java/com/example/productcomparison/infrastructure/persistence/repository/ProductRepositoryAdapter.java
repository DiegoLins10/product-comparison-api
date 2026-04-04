package com.example.productcomparison.infrastructure.persistence.repository;

import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.domain.repository.ProductRepository;
import com.example.productcomparison.infrastructure.persistence.mapper.ProductPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    public ProductRepositoryAdapter(SpringDataProductRepository springDataProductRepository) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(
                springDataProductRepository.findAll().stream().map(ProductPersistenceMapper::toDomain).toList()
        );
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        return Collections.unmodifiableList(
                springDataProductRepository.findByIdIn(ids).stream().map(ProductPersistenceMapper::toDomain).toList()
        );
    }
}
