package com.ecommerce.ecommerce_api.exception;

import java.time.LocalDateTime;

public record ErroResponseDTO(LocalDateTime timestamp,
                              int status,
                              String erro,
                              String mensagem) {
}
