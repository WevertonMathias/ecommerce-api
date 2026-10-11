package com.ecommerce.ecommerce_api.dto.adicionarItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AdicionarItemRequestDTO(

        @NotNull
        UUID varianteProdutoId,

        @Positive
        int quantidade
) {
}
