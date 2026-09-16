package com.ecommerce.ecommerce_api.dto.usuario;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha
) {
}
