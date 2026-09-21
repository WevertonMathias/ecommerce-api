package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.pedido.CriarPedidoRequestDTO;
import com.ecommerce.ecommerce_api.dto.pedido.PedidoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Pedido;
import com.ecommerce.ecommerce_api.entity.StatusPedido;
import com.ecommerce.ecommerce_api.mapper.PedidoMapper;
import com.ecommerce.ecommerce_api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<PedidoResponseDTO> criar(@PathVariable UUID usuarioId, @RequestBody CriarPedidoRequestDTO dto) {
        Pedido pedido = pedidoService.criarAPartirDoCarrinho(usuarioId, dto.endereco());
        PedidoResponseDTO responseDTO = pedidoMapper.toResponseDTO(pedido);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable UUID id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        PedidoResponseDTO responseDTO = pedidoMapper.toResponseDTO(pedido);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<Pedido> entidades = pedidoService.listarPorUsuario(usuarioId);
        List<PedidoResponseDTO> listaDTO = entidades.stream()
                .map(pedidoMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable UUID id, @RequestParam StatusPedido novoStatus) {
        Pedido entidades = pedidoService.atualizarStatus(id, novoStatus);
        PedidoResponseDTO responseDTO = pedidoMapper.toResponseDTO(entidades);
        return ResponseEntity.ok(responseDTO);
    }
}
