package com.example.productcomparison.application.usecase;

import com.example.productcomparison.application.dto.ProductDTO;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductsUseCase useCase;

    @Test
    void shouldReturnAllProductsWhenIdsNotProvided() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct(1L)));

        List<ProductDTO> result = useCase.execute(null);

        assertEquals(1, result.size());
        assertEquals("Produto 1", result.getFirst().name());
        verify(productRepository).findAll();
    }

    @Test
    void shouldReturnFilteredProductsWhenIdsProvided() {
        when(productRepository.findByIds(List.of(2L))).thenReturn(List.of(sampleProduct(2L)));

        List<ProductDTO> result = useCase.execute(List.of(2L));

        assertEquals(1, result.size());
        assertEquals(2L, result.getFirst().id());
        verify(productRepository).findByIds(List.of(2L));
    }

    private Product sampleProduct(Long id) {
        return new Product(id, "Produto " + id, "desc", new BigDecimal("100.00"), "url", 4.0, "M", 1.0, "azul", Map.of());
    }
}
