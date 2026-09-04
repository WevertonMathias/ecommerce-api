# Ecommerce API

API REST de um e-commerce, desenvolvida como projeto de estudo para praticar lógica de programação, regras de negócio e arquitetura de aplicações reais em Java.

> 🚧 Projeto em desenvolvimento. Este README será atualizado a cada etapa concluída.

## Sobre o projeto

Aplicação backend que simula as principais funcionalidades de uma loja virtual: catálogo de produtos com variações (tamanho/cor), carrinho de compras persistente, criação e acompanhamento de pedidos, e simulação de pagamento.

## Tecnologias

- Java 21
- Spring Boot 3 (Web, Data JPA, Security, Validation)
- PostgreSQL
- Flyway (versionamento de schema)
- JWT (autenticação)
- Docker / Docker Compose
- Springdoc OpenAPI (Swagger)
- JUnit 5 + Mockito + Testcontainers
- Lombok + MapStruct

## Arquitetura

Estrutura em camadas: `controller` → `service` → `repository`, com DTOs para entrada/saída (nunca expondo entidades JPA diretamente) e tratamento global de exceções.

Modelo de dados completo em [`docs/`](./docs).

## Como rodar o projeto localmente

> ⚠️ Seção será detalhada assim que o Docker Compose e o `pom.xml` estiverem prontos.

```bash
# Clonar o repositório
git clone https://github.com/SEU_USUARIO/ecommerce-api.git
cd ecommerce-api

# Subir o banco de dados via Docker
docker-compose up -d

# Rodar a aplicação
./mvnw spring-boot:run
```

## Documentação da API

Após subir a aplicação, o Swagger estará disponível em:
`http://localhost:8080/swagger-ui.html`

## Testes

```bash
./mvnw test
```

## Status do desenvolvimento

- [x] Modelagem do banco de dados
- [ ] Setup do projeto Spring Boot + Docker Compose
- [ ] Migrations Flyway
- [ ] Entidades JPA
- [ ] Autenticação JWT
- [ ] CRUD de produtos e categorias
- [ ] Carrinho de compras
- [ ] Pedidos e regras de negócio
- [ ] Pagamento (simulado)
- [ ] Testes unitários e de integração
- [ ] Documentação Swagger completa
- [ ] Deploy

## Autor

Desenvolvido por [seu nome] como projeto de estudo backend Java.
