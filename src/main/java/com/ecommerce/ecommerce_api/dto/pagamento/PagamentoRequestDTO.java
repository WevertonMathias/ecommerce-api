package com.ecommerce.ecommerce_api.dto.pagamento;

import com.ecommerce.ecommerce_api.entity.MetodoPagamento;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PagamentoRequestDTO(

        @NotNull
        UUID pedidoId,

        @NotNull
        MetodoPagamento metodo
) {
}
