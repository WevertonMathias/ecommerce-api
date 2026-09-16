package com.ecommerce.ecommerce_api.dto.carrinho;

import com.ecommerce.ecommerce_api.dto.itemCarrinho.ItemCarrinhoResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CarrinhoResponseDTO(
        UUID id,
        List<ItemCarrinhoResponseDTO> itens,
        BigDecimal total
) {
}
