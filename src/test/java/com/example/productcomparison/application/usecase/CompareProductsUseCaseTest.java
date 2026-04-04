package com.example.productcomparison.application.usecase;

import com.example.productcomparison.domain.exception.InvalidComparisonException;
import com.example.productcomparison.domain.exception.ProductNotFoundException;
import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompareProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CompareProductsUseCase useCase;

    @Test
    void shouldCompareBaseAndDynamicFields() {
        Product p1 = new Product(1L, "Phone A", "desc", new BigDecimal("1000.00"), "url", 4.5, "6.1\"", 0.2, "preto", Map.of("battery", "4000mAh"));
        Product p2 = new Product(2L, "Phone B", "desc", new BigDecimal("1200.00"), "url", 4.8, "6.7\"", 0.21, "branco", Map.of("battery", "5000mAh"));

        when(productRepository.findByIds(List.of(1L, 2L))).thenReturn(List.of(p1, p2));

        Map<String, Map<String, Object>> result = useCase.execute(List.of(1L, 2L), List.of("name", "battery"));

        assertEquals("Phone A", result.get("name").get("1"));
        assertEquals("Phone B", result.get("name").get("2"));
        assertEquals("4000mAh", result.get("battery").get("1"));
        assertEquals("5000mAh", result.get("battery").get("2"));
    }

    @Test
    void shouldIgnoreMissingFields() {
        Product p1 = new Product(1L, "Phone A", "desc", new BigDecimal("1000.00"), "url", 4.5, "6.1\"", 0.2, "preto", Map.of());
        when(productRepository.findByIds(List.of(1L))).thenReturn(List.of(p1));

        Map<String, Map<String, Object>> result = useCase.execute(List.of(1L), List.of("inexistente"));

        assertEquals(0, result.size());
    }

    @Test
    void shouldThrowWhenProductIdIsInvalid() {
        Product p1 = new Product(1L, "Phone A", "desc", new BigDecimal("1000.00"), "url", 4.5, "6.1\"", 0.2, "preto", Map.of());
        when(productRepository.findByIds(List.of(1L, 999L))).thenReturn(List.of(p1));

        assertThrows(ProductNotFoundException.class,
                () -> useCase.execute(List.of(1L, 999L), List.of("name")));
    }

    @Test
    void shouldThrowWhenFieldsMissing() {
        assertThrows(InvalidComparisonException.class,
                () -> useCase.execute(List.of(1L), List.of()));
    }
}
