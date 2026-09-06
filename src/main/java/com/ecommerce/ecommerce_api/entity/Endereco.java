package com.ecommerce.ecommerce_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    @Column(name = "recipient_name", nullable = false, length = 150)
    private String nomeDestinatario;

    @Column(name = "street", nullable = false, length = 200)
    private String rua;

    @Column(name = "number", nullable = false, length = 20)
    private String numero;

    @Column(name = "complement", length = 100)
    private String complemento;

    @Column(name = "neighborhood", nullable = false, length = 100)
    private String bairro;

    @Column(name = "city", nullable = false, length = 100)
    private String cidade;

    @Column(name = "state", nullable = false, length = 2)
    private String estado;

    @Column(name = "zip_code", nullable = false, length = 9)
    private String cep;
}
