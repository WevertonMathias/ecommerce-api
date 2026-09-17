package com.ecommerce.ecommerce_api.dto.pedido;

import com.ecommerce.ecommerce_api.entity.Endereco;
import com.ecommerce.ecommerce_api.entity.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        StatusPedido status,
        BigDecimal totalAmount,
        Endereco endereco,
        List<ItemPedidoResponseDTO> itens,
        LocalDateTime criadoEm
) {
}
