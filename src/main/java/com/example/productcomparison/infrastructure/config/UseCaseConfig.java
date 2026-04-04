package com.example.productcomparison.infrastructure.config;

import com.example.productcomparison.application.usecase.CompareProductsUseCase;
import com.example.productcomparison.application.usecase.GetProductByIdUseCase;
import com.example.productcomparison.application.usecase.GetProductsUseCase;
import com.example.productcomparison.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetProductsUseCase getProductsUseCase(ProductRepository productRepository) {
        return new GetProductsUseCase(productRepository);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase(ProductRepository productRepository) {
        return new GetProductByIdUseCase(productRepository);
    }

    @Bean
    public CompareProductsUseCase compareProductsUseCase(ProductRepository productRepository) {
        return new CompareProductsUseCase(productRepository);
    }
}
