package com.ecommerce.ecommerce_api.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProdutoRequestDTO(

        @NotBlank @Size(max = 150)
        String nome,

        @Size(max = 1000)
        String descricao,

        @NotNull
        UUID categoriaId
) {}
