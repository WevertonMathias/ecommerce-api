package com.ecommerce.ecommerce_api.dto.varianteProduto;

import java.math.BigDecimal;
import java.util.UUID;

public record VarianteProdutoResponseDTO(
        UUID id,
        String tamanho,
        String cor,
        String sku,
        String produtoNome,
        BigDecimal preco,
        int quantidadeEstoque
) {
}
