package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.varianteProduto.VarianteProdutoRequestDTO;
import com.ecommerce.ecommerce_api.dto.varianteProduto.VarianteProdutoResponseDTO;
import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VarianteProdutoMapper {

    @Mapping(target = "produto", ignore = true)
    VarianteProduto toEntity(VarianteProdutoRequestDTO dto);

    @Mapping(target = "produtoNome", source = "produto.nome")
    VarianteProdutoResponseDTO toResponseDTO(VarianteProduto varianteProduto);
}
