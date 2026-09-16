package com.ecommerce.ecommerce_api.dto.varianteProduto;

import java.math.BigDecimal;
import java.util.UUID;

public record VarianteProdutoRequestDTO(
        String tamanho,
        String cor,
        String sku,
        BigDecimal preco,
        UUID produdoId
) {
}
