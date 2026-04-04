package com.example.productcomparison.infrastructure.config;

import com.example.productcomparison.infrastructure.persistence.entity.ProductJpaEntity;
import com.example.productcomparison.infrastructure.persistence.repository.SpringDataProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(SpringDataProductRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.save(ProductJpaEntity.of(
                    "Smartphone X1",
                    "Smartphone premium com câmera avançada",
                    new BigDecimal("3499.90"),
                    "https://example.com/images/smartphone-x1.jpg",
                    4.7,
                    "6.1\"",
                    0.174,
                    "preto",
                    Map.of("battery", "4500mAh", "storage", "256GB", "ram", "8GB")
            ));

            repository.save(ProductJpaEntity.of(
                    "Camiseta Sport Dry",
                    "Camiseta esportiva respirável",
                    new BigDecimal("99.90"),
                    "https://example.com/images/camiseta-sport.jpg",
                    4.3,
                    "M",
                    0.200,
                    "azul",
                    Map.of("material", "poliester", "gender", "unissex", "sleeve", "curta")
            ));

            repository.save(ProductJpaEntity.of(
                    "Smartphone Z Fold",
                    "Smartphone dobrável de alta performance",
                    new BigDecimal("7999.00"),
                    "https://example.com/images/smartphone-zfold.jpg",
                    4.8,
                    "7.2\"",
                    0.280,
                    "prata",
                    Map.of("battery", "5000mAh", "storage", "512GB", "ram", "12GB")
            ));
        };
    }
}
