package com.ecommerce.ecommerce_api.dto.varianteProduto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record VarianteProdutoRequestDTO(

        @NotBlank
        String tamanho,

        @NotBlank
        String cor,

        @NotBlank
        String sku,

        @NotNull
        @Positive
        BigDecimal preco,

        @NotNull
        UUID produtoId,

        @PositiveOrZero(message = "O estoque não pode ser negativo")
        int quantidadeEstoque
) {
}
