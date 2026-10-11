package com.ecommerce.ecommerce_api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank
        String email,

        @NotBlank
        String senha
) {
}
