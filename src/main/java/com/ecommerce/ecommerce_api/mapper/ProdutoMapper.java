package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.produto.ProdutoRequestDTO;
import com.ecommerce.ecommerce_api.dto.produto.ProdutoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "categoria", ignore = true)
    Produto toEntity(ProdutoRequestDTO dto);

    @Mapping(target = "categoriaNome", source = "categoria.nome")
    ProdutoResponseDTO toResponseDTO(Produto produto);

}
