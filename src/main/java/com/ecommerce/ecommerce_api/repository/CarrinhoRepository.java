package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {
    Optional<Carrinho> findByUsuarioId(UUID usuarioId);
}
