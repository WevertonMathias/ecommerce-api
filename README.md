# E-commerce API

API REST de e-commerce construída do zero em Java + Spring Boot, como projeto de estudo para minha primeira vaga como desenvolvedor backend.

## O que a API faz

- Cadastro de usuários com autenticação (JWT em andamento)
- Categorias e produtos
- Variantes de produto (tamanho, cor) — preço e estoque controlados por variante, não pelo produto
- Carrinho de compras (1:1 por usuário)
- Pedidos, com máquina de estados: `PENDENTE → PAGO → ENVIADO → ENTREGUE` (ou `CANCELADO`, antes de `ENVIADO`)
- Pagamento simulado, vinculado 1:1 ao pedido
- Tratamento global de exceções: erros retornam `404` (recurso não encontrado) ou `400` (regra de negócio violada), com corpo de resposta padronizado — nada de stacktrace vazando pro cliente

## Stack

- **Java 21** + **Spring Boot 4**
- **PostgreSQL** com **Flyway** (controle de versão do schema via migrations)
- **Spring Data JPA**
- **Spring Security** + **JWT** (em andamento)
- **MapStruct** (conversão Entity ↔ DTO)
- **Lombok**
- **Docker** (banco de dados em container)
- **springdoc-openapi** (Swagger)
- JUnit 5 + Mockito + Testcontainers (planejado)

## Modelo de dados

10 tabelas principais: `users`, `roles`, `user_roles`, `categories`, `products`, `product_variants`, `carts`, `cart_items`, `orders`, `order_items`, `payments`.

Algumas decisões de modelagem:
- Preço e estoque ficam na **variante**, não no produto — o mesmo produto pode ter variações com preços e disponibilidade diferentes.
- Endereço de entrega é **embutido** no pedido (`@Embeddable`), não uma tabela separada — cada pedido tem seu próprio endereço, sem necessidade de reaproveitamento.
- `order_items.unit_price` é um **snapshot**: guarda o preço no momento da compra, imutável mesmo que o preço da variante mude depois.
- Carrinho é **1:1** com o usuário, criado automaticamente (find-or-create) na primeira vez que é acessado.

## Arquitetura

Camadas separadas por responsabilidade:

\```
Entity → Repository → Service → DTO/Mapper → Controller
\```

- **Entity**: espelha o banco de dados
- **Repository**: acesso a dados (Spring Data JPA + Query Methods)
- **Service**: regras de negócio, validações, máquina de estados
- **DTO/Mapper**: contrato de entrada/saída da API (MapStruct), nunca expondo a entidade diretamente
- **Controller**: camada HTTP, sem lógica de negócio

## Status atual

- [x] Entidades e relacionamentos
- [x] Repositories
- [x] Services (regras de negócio completas)
- [x] DTOs e Mappers
- [x] Controllers (CRUD completo de todos os recursos)
- [x] Tratamento global de exceções
- [ ] Autenticação JWT (login funcionando; filtro de validação em andamento)
- [ ] Testes automatizados (JUnit + Mockito + Testcontainers)
- [ ] Documentação Swagger
- [ ] Dockerfile + deploy

## Como rodar localmente

Pré-requisitos: Java 21, Docker, Maven (ou usar o `./mvnw` incluso no projeto).

\```bash
# Sobe o banco de dados PostgreSQL
docker-compose up -d

# Roda a aplicação (Flyway aplica as migrations automaticamente)
./mvnw spring-boot:run
\```

A API sobe em `http://localhost:8080`. Health-check disponível em `/actuator/health`.

## Sobre o projeto

Esse é um projeto pessoal de estudo, construído com o objetivo de aprender Spring Boot na prática e treinar decisões reais de arquitetura e modelagem de dados — não apenas seguir tutoriais, mas entender o porquê de cada escolha.