package com.ecommerce.ecommerce_api.dto.auth;

public record LoginRequestDTO(
        String email,
        String senha
) {
}
