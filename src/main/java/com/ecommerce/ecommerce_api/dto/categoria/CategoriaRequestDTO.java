package com.ecommerce.ecommerce_api.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank @Size(max = 100)
        String nome,

        @Size(max = 500)
        String descricao
) {
}
