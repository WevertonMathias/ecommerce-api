package com.ecommerce.ecommerce_api.dto.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDateTime criadoEm
) {
}
