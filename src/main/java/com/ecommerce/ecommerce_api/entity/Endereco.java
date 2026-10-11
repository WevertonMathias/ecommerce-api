package com.ecommerce.ecommerce_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @NotBlank
    @Size(max = 150)
    @Column(name = "recipient_name", nullable = false, length = 150)
    private String nomeDestinatario;

    @NotBlank
    @Size(max = 200)
    @Column(name = "street", nullable = false, length = 200)
    private String rua;

    @NotBlank
    @Size(max = 20)
    @Column(name = "number", nullable = false, length = 20)
    private String numero;

    @Size(max = 100)
    @Column(name = "complement", length = 100)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "neighborhood", nullable = false, length = 100)
    private String bairro;

    @NotBlank
    @Size(max = 100)
    @Column(name = "city", nullable = false, length = 100)
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2, message = "O estado deve ter 2 letras, por exemplo SP")
    @Column(name = "state", nullable = false, length = 2)
    private String estado;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido, use o formato 12345-678")
    @Column(name = "zip_code", nullable = false, length = 9)
    private String cep;
}