package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, UUID> {
    Optional<ItemCarrinho> findByCarrinhoIdAndVarianteProdutoId(UUID carrinhoID, UUID varianteProdutoID);
}
