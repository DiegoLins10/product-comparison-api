package com.example.productcomparison.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProductTest {

    @Test
    void shouldProtectAttributesFromExternalMutation() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("battery", "5000mAh");

        Product product = new Product(1L, "Phone", "desc", new BigDecimal("10.00"), "url", 4.5, "M", 0.2, "preto", attrs);

        attrs.put("battery", "1000mAh");
        Map<String, Object> returned = product.getAttributes();
        returned.put("battery", "2000mAh");

        assertEquals("5000mAh", product.getAttributes().get("battery"));
        assertNotEquals(attrs.get("battery"), product.getAttributes().get("battery"));
    }
}
