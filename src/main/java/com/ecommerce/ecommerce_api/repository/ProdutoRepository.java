package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByCategoriaId(UUID categoriaId);
    List<Produto> findByAtivoTrue();
}
