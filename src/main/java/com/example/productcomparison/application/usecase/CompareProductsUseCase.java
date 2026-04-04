package com.example.productcomparison.application.usecase;

import com.example.productcomparison.domain.exception.InvalidComparisonException;
import com.example.productcomparison.domain.exception.ProductNotFoundException;
import com.example.productcomparison.domain.model.Product;
import com.example.productcomparison.domain.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareProductsUseCase {

    private static final Logger log = LoggerFactory.getLogger(CompareProductsUseCase.class);
    private static final Set<String> BASE_FIELDS = Set.of(
            "id", "name", "description", "price", "imageUrl", "rating", "size", "weight", "color"
    );

    private final ProductRepository productRepository;

    public CompareProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<String, Map<String, Object>> execute(List<Long> ids, List<String> fields) {
        if (ids == null || ids.isEmpty()) {
            throw new InvalidComparisonException("É necessário informar ao menos um id de produto");
        }
        if (fields == null || fields.isEmpty()) {
            throw new InvalidComparisonException("É necessário informar ao menos um campo para comparação");
        }

        List<Product> products = productRepository.findByIds(ids);
        validateAllProductsFound(ids, products);

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();

        for (String field : fields) {
            Map<String, Object> byProduct = new LinkedHashMap<>();

            for (Product product : products) {
                Object value = resolveFieldValue(product, field);
                if (value != null) {
                    byProduct.put(product.getId().toString(), value);
                }
            }

            if (!byProduct.isEmpty()) {
                result.put(field, byProduct);
            }
        }

        log.info("Comparação concluída para {} produtos e {} campos", products.size(), result.size());
        return result;
    }

    private void validateAllProductsFound(List<Long> ids, List<Product> products) {
        Set<Long> foundIds = products.stream().map(Product::getId).collect(java.util.stream.Collectors.toSet());
        List<Long> missingIds = ids.stream().filter(id -> !foundIds.contains(id)).toList();

        if (!missingIds.isEmpty()) {
            throw new ProductNotFoundException("Produtos não encontrados para ids: " + missingIds);
        }
    }

    private Object resolveFieldValue(Product product, String field) {
        if (BASE_FIELDS.contains(field)) {
            return getBaseFieldWithReflection(product, field);
        }

        return new HashMap<>(product.getAttributes()).get(field);
    }

    private Object getBaseFieldWithReflection(Product product, String fieldName) {
        try {
            Field field = Product.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(product);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
