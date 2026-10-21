# E-commerce API

API REST de e-commerce construída do zero em Java + Spring Boot, como projeto de estudo para minha primeira vaga como desenvolvedor backend.

Documentação interativa (Swagger): `http://localhost:8080/swagger-ui.html`

## O que a API faz

- Cadastro e login de usuários, com autenticação via **JWT**
- Categorias e produtos
- **Variantes** de produto (tamanho, cor), com preço e estoque controlados por variante, não pelo produto
- Estoque inicial na criação, reposição e baixa, com validação de quantidade
- Carrinho de compras (1:1 por usuário)
- Pedidos com máquina de estados: `PENDENTE → PAGO → ENVIADO → ENTREGUE` (ou `CANCELADO`, antes de `ENVIADO`)
- Pagamento simulado, vinculado 1:1 ao pedido
- Erros padronizados em JSON, sem stacktrace vazando para o cliente

## Segurança

Autenticação por token JWT (`Authorization: Bearer <token>`) e dois papéis: `ADMIN` e `CLIENTE`. Todo cadastro novo entra como `CLIENTE`.

| Recurso | Quem pode |
|---|---|
| Login, cadastro de usuário, Swagger, health check | Público |
| Consultar categorias, produtos e variantes | Qualquer usuário logado |
| Criar, alterar e inativar categorias, produtos e variantes; ajustar estoque | Somente `ADMIN` |
| Listar todos os usuários; alterar o status de um pedido | Somente `ADMIN` |
| Carrinho, pedidos, pagamentos e dados do usuário | O próprio dono, ou `ADMIN` |

A regra do dono evita que um cliente leia o carrinho ou os pedidos de outro trocando o id na URL. Ela é feita com `@PreAuthorize` e um componente dedicado (`AutorizacaoService`).

Respostas de erro seguem o mesmo formato:

```json
{ "timestamp": "...", "status": 400, "erro": "Dados inválidos", "mensagem": "nome: O nome é obrigatório" }
```

| Status | Quando |
|---|---|
| `400` | Dado inválido ou regra de negócio violada (estoque insuficiente, email duplicado...) |
| `401` | Sem token, ou token inválido/expirado |
| `403` | Logado, mas sem permissão |
| `404` | Recurso não encontrado |

## Stack

- **Java 21** + **Spring Boot 4**
- **PostgreSQL** + **Flyway** (schema versionado por migrations)
- **Spring Data JPA**
- **Spring Security** + **JWT** (jjwt)
- **Bean Validation** (validação de entrada nos DTOs)
- **MapStruct** (Entity ↔ DTO) e **Lombok**
- **springdoc-openapi** (Swagger UI)
- **JUnit**, **Mockito** e **Testcontainers**
- **Docker** (build em duas etapas) e Docker Compose

## Modelo de dados

11 tabelas: `users`, `roles`, `user_roles`, `categories`, `products`, `product_variants`, `carts`, `cart_items`, `orders`, `order_items`, `payments`.

Decisões de modelagem:
- Preço e estoque ficam na **variante**, não no produto: o mesmo produto pode ter versões com preço e disponibilidade diferentes.
- O endereço de entrega é **embutido** no pedido (`@Embeddable`), sem tabela própria.
- `order_items.unit_price` é um **snapshot**: guarda o preço no momento da compra e não muda se o preço da variante mudar depois.
- O carrinho é **1:1** com o usuário e é criado automaticamente no primeiro acesso.
- - Exclusão é lógica (campo ativo) em usuários, produtos e variantes, para preservar o histórico dos pedidos. A categoria é removida de fato.

## Arquitetura

```
Entity → Repository → Service → DTO/Mapper → Controller
```

- **Entity**: espelha o banco
- **Repository**: acesso a dados (Spring Data JPA)
- **Service**: regras de negócio, validações e máquina de estados
- **DTO/Mapper**: contrato de entrada e saída, sem expor entidades
- **Controller**: camada HTTP, sem lógica de negócio

## Testes

- **Unitários**: todos os Services, com JUnit e Mockito (repositórios mockados).
- **Integração**: `UsuarioRepository` contra um PostgreSQL real e descartável, via Testcontainers.

```bash
./mvnw test
```

Os testes de integração precisam do **Docker em execução**.

## Como rodar localmente

Pré-requisitos: Docker (e Java 21, se for rodar fora do container).

### Opção 1: tudo no Docker

```bash
docker-compose up --build
```

Sobe o PostgreSQL e a API. O Flyway aplica as migrations sozinho.

### Opção 2: banco no Docker, aplicação pela IDE ou Maven

```bash
docker-compose up -d postgres
./mvnw spring-boot:run
```

Em ambos os casos a API fica em `http://localhost:8080`, e o health check em `/actuator/health`.

### Variáveis de ambiente

Todas têm valor padrão para desenvolvimento local.

| Variável | Padrão | Para que serve |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/ecommerce` | Conexão com o banco |
| `SPRING_DATASOURCE_USERNAME` | `admin` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | `admin123` | Senha do banco |
| `JWT_SECRET` | valor de exemplo | Chave de assinatura dos tokens. **Em produção, use um valor longo e aleatório (mínimo 32 caracteres)** |
| `JWT_EXPIRATION_MS` | `3600000` (1 hora) | Validade do token |
| `PORT` | `8080` | Porta da aplicação (plataformas de nuvem definem esse valor) |

### Criando o primeiro administrador

O cadastro sempre cria um `CLIENTE`. Para promover um usuário a `ADMIN`:

```bash
docker exec -it ecommerce-postgres psql -U admin -d ecommerce -c "INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'seu@email.com' AND r.name = 'ADMIN';"
```

Depois, faça login de novo: os papéis ficam dentro do token.

### Usando o Swagger com autenticação

1. Em `POST /auth/login`, envie email e senha e copie o token da resposta, sem as aspas.
2. Clique em **Authorize**, cole o token e confirme.

## Status

- [x] Modelagem e migrations
- [x] Entidades, repositories, services, DTOs/mappers e controllers
- [x] Tratamento global de exceções
- [x] Autenticação JWT
- [x] Autorização por papel e por dono
- [x] Validação de entrada
- [x] Swagger com suporte a JWT
- [x] Testes unitários dos Services
- [x] Teste de integração com Testcontainers (UsuarioRepository)
- [x] Dockerfile e Docker Compose
- [x] Testes de integração dos demais repositories
- [x] Deploy (Render)

## Sobre o projeto

Projeto pessoal de estudo, feito para praticar Spring Boot e decisões reais de arquitetura e modelagem. A ideia é entender o porquê de cada escolha, e não só seguir tutorial.