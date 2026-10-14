# 🛒 E-commerce API RESTful

API REST de e-commerce construída do zero em Java + Spring Boot, desenvolvida como projeto de portfólio e estudo avançado de arquitetura backend, regras de negócio transacionais e segurança.

## 🚀 O que a API faz

- **Autenticação & Segurança:** Cadastro e login de usuários com tokens JWT e controle de acesso por perfis (`CLIENTE` e `ADMIN`).
- **Catálogo Inteligente:** Gestão de Categorias, Produtos e **Variantes de produto** (tamanho, cor, SKU). O preço e o estoque pertencem à variante, permitindo variações independentes para o mesmo produto.
- **Carrinho & Pedidos:** Carrinho de compras dinâmico atrelado ao usuário. O fluxo de pedidos segue uma máquina de estados rigorosa: `PENDENTE → PAGO → ENVIADO → ENTREGUE` (ou `CANCELADO`, desde que antes de `ENVIADO`).
- **Pagamento Simulado:** Módulo de pagamento vinculado 1:1 ao pedido.
- **Tratamento Global de Erros:** Exceções tratadas via `@ControllerAdvice`, retornando códigos HTTP padronizados (`400`, `404`, etc.) sem expor stacktraces ao cliente.

## 🛠️ Stack Tecnológica

- **Linguagem & Framework:** Java 21 + Spring Boot
- **Persistência:** Spring Data JPA / Hibernate + PostgreSQL
- **Migrações:** Flyway (versionamento estruturado do schema)
- **Segurança:** Spring Security + JJWT
- **Mapeamento & Boilerplate:** MapStruct + Lombok
- **Documentação:** Springdoc OpenAPI (Swagger UI)
- **Containerização:** Docker & Docker Compose

## 📐 Decisões de Modelagem de Dados

O banco de dados foi estruturado com foco em consistência e regras reais de e-commerce:
- **Preço e Estoque na Variante:** Produtos genéricos servem apenas como agrupadores; o preço real e a disponibilidade ficam na tabela de variantes (`product_variants`).
- **Endereço Embutido:** O endereço de entrega é `@Embeddable` diretamente na tabela de pedidos, garantindo que o histórico de entrega do pedido permaneça imutável mesmo se o usuário alterar seu endereço cadastrado no perfil.
- **Snapshot de Preço:** `order_items.unit_price` armazena o preço exato no momento da compra, blindando o pedido contra futuras alterações de preço no catálogo.
- **Carrinho Find-or-Create:** O carrinho é criado automaticamente na primeira interação do usuário.

## 📐 Arquitetura

O projeto segue estritamente a **Arquitetura em Camadas**, garantindo separação de responsabilidades:
```text
Entity → Repository → Service → DTO/Mapper → Controller