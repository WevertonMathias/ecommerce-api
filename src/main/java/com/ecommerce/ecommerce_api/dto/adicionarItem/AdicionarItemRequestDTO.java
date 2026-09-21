package com.ecommerce.ecommerce_api.dto.adicionarItem;

import java.util.UUID;

public record AdicionarItemResponseDTO(
        UUID varianteProdutoId,
        int quantidade
) {
}
