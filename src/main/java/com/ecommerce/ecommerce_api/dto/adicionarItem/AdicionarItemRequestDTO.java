package com.ecommerce.ecommerce_api.dto.adicionarItem;

import java.util.UUID;

public record AdicionarItemRequestDTO(
        UUID varianteProdutoId,
        int quantidade
) {
}
