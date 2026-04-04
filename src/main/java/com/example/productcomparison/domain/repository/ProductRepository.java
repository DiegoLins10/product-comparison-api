package com.example.productcomparison.domain.repository;

import com.example.productcomparison.domain.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByIds(List<Long> ids);
}
