package com.ecommerce.ecommerce_api.dto.produto;

import java.util.UUID;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        UUID categoriaId
) {}
