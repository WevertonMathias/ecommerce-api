package com.ecommerce.ecommerce_api.dto.pagamento;

import com.ecommerce.ecommerce_api.entity.MetodoPagamento;

import java.util.UUID;

public record PagamentoRequestDTO(
        UUID pedidoId,
        MetodoPagamento metodo
) {
}
