package com.ecommerce.ecommerce_api.mapper;
import com.ecommerce.ecommerce_api.dto.categoria.CategoriaRequestDTO;
import com.ecommerce.ecommerce_api.dto.categoria.CategoriaResponseDTO;
import com.ecommerce.ecommerce_api.entity.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toEntity(CategoriaRequestDTO dto);
    CategoriaResponseDTO toResponseDTO(Categoria categoria);

}
