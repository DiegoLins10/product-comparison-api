package com.example.productcomparison.presentation.controller;

import com.example.productcomparison.application.dto.ProductDTO;
import com.example.productcomparison.application.usecase.CompareProductsUseCase;
import com.example.productcomparison.application.usecase.GetProductByIdUseCase;
import com.example.productcomparison.application.usecase.GetProductsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final CompareProductsUseCase compareProductsUseCase;

    public ProductController(
            GetProductsUseCase getProductsUseCase,
            GetProductByIdUseCase getProductByIdUseCase,
            CompareProductsUseCase compareProductsUseCase
    ) {
        this.getProductsUseCase = getProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.compareProductsUseCase = compareProductsUseCase;
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos ou filtra por ids.")
    @GetMapping
    public List<ProductDTO> getProducts(
            @Parameter(description = "Lista de IDs separados por vírgula", example = "1,2")
            @RequestParam(required = false) List<Long> ids
    ) {
        return getProductsUseCase.execute(ids);
    }

    @Operation(summary = "Buscar produto por id")
    @GetMapping("/{id}")
    public ProductDTO getProductById(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id
    ) {
        return getProductByIdUseCase.execute(id);
    }

    @Operation(summary = "Comparar produtos", description = "Compara produtos por campos base e atributos dinâmicos.")
    @GetMapping("/comparison")
    public Map<String, Map<String, Object>> compareProducts(
            @Parameter(description = "Lista de IDs separados por vírgula", example = "1,2")
            @RequestParam @NotEmpty(message = "O parâmetro ids é obrigatório") List<Long> ids,
            @Parameter(description = "Campos para comparação (base e atributos dinâmicos)", example = "name,price,battery")
            @RequestParam @NotEmpty(message = "O parâmetro fields é obrigatório") List<String> fields
    ) {
        return compareProductsUseCase.execute(ids, fields);
    }
}
