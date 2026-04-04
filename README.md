# 🚀 Product Comparison API

API REST para **comparação dinâmica de produtos** com **Java 21 + Spring Boot**, construída com foco em **DDD tático** e **Clean Architecture**.

## 📚 Sumário
- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Como Executar](#-como-executar)
- [Documentação e Acesso](#-documentação-e-acesso)
- [Exemplos de Requisição](#-exemplos-de-requisição)
- [Regras de Negócio](#-regras-de-negócio)
- [Testes](#-testes)
- [Estrutura de Pastas](#-estrutura-de-pastas)

## 🎯 Visão Geral
Esta API permite:
- Listar produtos
- Buscar produto por ID
- Comparar múltiplos produtos por campos dinâmicos

A comparação suporta:
- Campos base (`id`, `name`, `description`, `price`, etc.)
- Campos flexíveis via `attributes` (ex.: `battery`, `ram`, `material`)

---

## 🧱 Arquitetura
A aplicação segue separação por camadas:

- `domain`: regras de negócio puras, entidades e contratos
- `application`: casos de uso e orquestração
- `infrastructure`: persistência, adapters e configurações
- `presentation`: endpoints REST e tratamento de exceções

### ✅ Decisões importantes
- Domínio sem dependência de framework
- Controllers finos (sem regra de negócio)
- Mapeamento explícito entre domínio e persistência
- Comparação dinâmica sem hardcode de campos

---

## 🛠 Tecnologias
- Java 21
- Spring Boot 3.3+
- Maven
- Spring Web
- Spring Data JPA
- H2 (in-memory)
- Spring Validation
- SpringDoc OpenAPI (Swagger)
- JUnit 5 + Mockito

---

## ▶️ Como Executar
### Pré-requisitos
- Java 21
- Maven 3.9+

### Subir aplicação
```bash
mvn spring-boot:run
```

---

## 🌐 Documentação e Acesso
Com a aplicação rodando em `8080`:

- Swagger UI: `http://localhost:8080/swagger`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

---

## 🧪 Exemplos de Requisição
### 1) Listar todos os produtos
```bash
curl "http://localhost:8080/products"
```

### 2) Filtrar produtos por IDs
```bash
curl "http://localhost:8080/products?ids=1,2"
```

### 3) Buscar produto por ID
```bash
curl "http://localhost:8080/products/1"
```

### 4) Comparar produtos por campos
```bash
curl "http://localhost:8080/products/comparison?ids=1,2&fields=name&fields=price&fields=battery"
```

---

## 📏 Regras de Negócio
- Se algum ID informado não existir: retorna `404` (`ProductNotFoundException`)
- Campos inexistentes na comparação são ignorados
- Se parâmetros obrigatórios faltarem: retorna `400`
- Comparação dinâmica usa:
  - reflexão para campos base
  - mapa `attributes` para campos flexíveis

---

## ✅ Testes
Executar:
```bash
mvn test
```

Cobertura implementada:
- Testes unitários de domínio
- Testes unitários de casos de uso
- Teste de integração HTTP data-driven com cenários JSON em:
  - `src/test/resources/testcases/http00.json`
  - `src/test/resources/testcases/http01.json`
  - `src/test/resources/testcases/http02.json`

Runner de integração:
- `HttpJsonDynamicIntegrationTest`

---

## 🗂 Estrutura de Pastas
```text
src/
  main/
    java/com/example/productcomparison/
      domain/
      application/
      infrastructure/
      presentation/
    resources/
  test/
    java/com/example/productcomparison/
    resources/testcases/
```

---

## 📝 Observação
Não houve alteração adicional de regra de negócio além do escopo funcional solicitado para o projeto.
