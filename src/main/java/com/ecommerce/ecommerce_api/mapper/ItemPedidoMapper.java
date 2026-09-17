package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.pedido.ItemPedidoResponseDTO;
import com.ecommerce.ecommerce_api.entity.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    @Mapping(target = "varianteProdutoNome", source = "varianteProduto.produto.nome")
    ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido);
}
