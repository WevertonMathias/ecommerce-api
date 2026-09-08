package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    Optional<Categoria> findByNome(String nome);
    boolean existsByNome(String nome);
}
