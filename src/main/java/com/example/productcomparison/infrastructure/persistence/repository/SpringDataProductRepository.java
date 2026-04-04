package com.example.productcomparison.infrastructure.persistence.repository;

import com.example.productcomparison.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, Long> {

    List<ProductJpaEntity> findByIdIn(List<Long> ids);
}
