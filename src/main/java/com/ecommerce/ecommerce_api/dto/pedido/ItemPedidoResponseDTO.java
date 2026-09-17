package com.ecommerce.ecommerce_api.dto.pedido;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(
        UUID id,
        String varianteProdutoNome,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subTotal

) {
}
