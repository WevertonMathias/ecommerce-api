package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.pagamento.PagamentoRequestDTO;
import com.ecommerce.ecommerce_api.dto.pagamento.PagamentoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Pagamento;
import com.ecommerce.ecommerce_api.mapper.PagamentoMapper;
import com.ecommerce.ecommerce_api.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> processarPagamento(@RequestBody PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoService.processarPagamento(dto.pedidoId(), dto.metodo());
        PagamentoResponseDTO responseDTO = pagamentoMapper.toResponseDTO(pagamento);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorPedido(@PathVariable UUID pedidoId) {
        Pagamento entidades = pagamentoService.buscarPorPedido(pedidoId);
        PagamentoResponseDTO responseDTO = pagamentoMapper.toResponseDTO(entidades);
        return ResponseEntity.ok(responseDTO);
    }
}
