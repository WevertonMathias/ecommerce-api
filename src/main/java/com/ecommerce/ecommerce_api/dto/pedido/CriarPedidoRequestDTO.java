package com.ecommerce.ecommerce_api.dto.pedido;

import com.ecommerce.ecommerce_api.entity.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CriarPedidoRequestDTO(

        @Valid
        @NotNull
        Endereco endereco
) {
}
