package com.ecommerce.ecommerce_api.dto.pagamento;

import com.ecommerce.ecommerce_api.entity.MetodoPagamento;
import com.ecommerce.ecommerce_api.entity.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoResponseDTO(
        UUID id,
        UUID pedidoId,
        MetodoPagamento metodo,
        StatusPagamento status,
        BigDecimal amount,
        LocalDateTime pagoEm,
        LocalDateTime criadoEm
) {
}
