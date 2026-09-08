package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Pedido;
import com.ecommerce.ecommerce_api.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByUsuarioId(UUID usuarioId);
    List<Pedido> findByUsuarioIdOrderByCriadoEmDesc(UUID usuarioId);
    List<Pedido> findByStatus(StatusPedido status);
}
