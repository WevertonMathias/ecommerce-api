package com.ecommerce.ecommerce_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "id")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "description", length = 500)
    private String descricao;
}
