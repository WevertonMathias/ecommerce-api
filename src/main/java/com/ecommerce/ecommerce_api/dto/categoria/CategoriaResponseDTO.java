package com.ecommerce.ecommerce_api.dto.categoria;

import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String nome,
        String descricao
) {
}
