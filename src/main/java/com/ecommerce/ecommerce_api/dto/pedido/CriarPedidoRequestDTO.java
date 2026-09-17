package com.ecommerce.ecommerce_api.dto.pedido;

import com.ecommerce.ecommerce_api.entity.Endereco;

import java.math.BigDecimal;
import java.util.UUID;

public record CriarPedidoRequestDTO(
        Endereco endereco
) {
}
