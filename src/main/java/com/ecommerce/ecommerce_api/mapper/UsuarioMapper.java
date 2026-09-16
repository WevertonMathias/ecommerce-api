package com.ecommerce.ecommerce_api.mapper;

import com.ecommerce.ecommerce_api.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.ecommerce_api.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.ecommerce_api.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "senhaHash", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
