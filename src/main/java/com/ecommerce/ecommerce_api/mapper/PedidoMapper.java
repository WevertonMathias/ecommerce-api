package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.pedido.PedidoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ItemPedidoMapper.class)
public interface PedidoMapper {
    PedidoResponseDTO toResponseDTO(Pedido pedido);
}
