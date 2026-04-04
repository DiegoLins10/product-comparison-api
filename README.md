# Item Comparison API

API REST para comparação dinâmica de produtos, construída com Java 21, Spring Boot e Maven, seguindo DDD tático e Clean Architecture.

## 1. Arquitetura (DDD + Clean Architecture)

A solução separa responsabilidades em quatro camadas:

- `domain`: núcleo de negócio puro (entidades, exceções, portas de repositório)
- `application`: casos de uso, DTOs e orquestração
- `infrastructure`: persistência JPA, adaptadores e configuração
- `presentation`: controllers REST e tratamento de erros

### Decisão-chave

- O domínio não depende de Spring.
- Comparação dinâmica usa reflexão para campos base e `attributes` para campos flexíveis.
- Controllers são finos: delegam para use cases.
- Entidades JPA são separadas dos modelos de domínio.

## 2. Responsabilidades por camada

- `domain/model/Product`: agregado raiz e estado do produto
- `domain/repository/ProductRepository`: contrato de acesso a dados
- `application/usecase/GetProductsUseCase`: busca produtos com/sem filtro por IDs
- `application/usecase/CompareProductsUseCase`: compara campos dinâmicos entre produtos
- `infrastructure/persistence`: implementação JPA e mapper infra <-> domínio
- `presentation/controller/ProductController`: endpoints `/products` e `/products/comparison`
- `presentation/exception/GlobalExceptionHandler`: padronização de erros da API

## 3. Como executar

### Pré-requisitos

- Java 21
- Maven 3.9+

### Rodando

```bash
mvn spring-boot:run
```

### Swagger e H2

- Swagger UI: `http://localhost:8080/swagger`
- OpenAPI: `http://localhost:8080/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

## 4. Exemplos de requisição

### Listar todos os produtos

```bash
curl "http://localhost:8080/products"
```

### Listar produtos por IDs

```bash
curl "http://localhost:8080/products?ids=1,2"
```

### Comparar produtos por campos dinâmicos

```bash
curl "http://localhost:8080/products/comparison?ids=1,2&fields=name&fields=price&fields=battery"
```

## 5. Regras e comportamento

- IDs inválidos disparam `ProductNotFoundException` (404).
- Campos inexistentes na comparação são ignorados.
- Campos base (`id`, `name`, `price` etc.) são resolvidos via reflexão.
- Campos flexíveis são resolvidos via mapa `attributes`.

## 6. Testes

Executar:

```bash
mvn test
```

Cobertura implementada:

- Unitários de domínio (`ProductTest`)
- Unitários de casos de uso (`GetProductsUseCaseTest`, `CompareProductsUseCaseTest`)
- Integração de controller (`ProductControllerIntegrationTest`)

## 7. Regra de negócio

Não houve alteração adicional de regra de negócio além do que foi especificado no prompt funcional.
