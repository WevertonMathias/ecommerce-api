package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VarianteProdutoRepository extends JpaRepository<VarianteProduto, UUID> {
    List<VarianteProduto> findByProdutoId(UUID produtoId);
    Optional<VarianteProduto> findBySku(String sku);
    boolean existsBySku(String sku);
}
