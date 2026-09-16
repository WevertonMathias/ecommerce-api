package com.ecommerce.ecommerce_api.dto.produto;

import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        boolean ativo,
        String categoriaNome
) {
}
