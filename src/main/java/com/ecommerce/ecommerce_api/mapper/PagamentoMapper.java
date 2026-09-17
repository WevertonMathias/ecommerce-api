package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.pagamento.PagamentoRequestDTO;
import com.ecommerce.ecommerce_api.dto.pagamento.PagamentoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {
    @Mapping(target = "pedido", ignore = true)
    Pagamento toEntity(PagamentoRequestDTO dto);

    @Mapping(target = "pedidoId", source = "pedido.id")
    PagamentoResponseDTO toResponseDTO(Pagamento pagamento);
}
