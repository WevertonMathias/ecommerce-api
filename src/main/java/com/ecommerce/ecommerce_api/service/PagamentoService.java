package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.*;
import com.ecommerce.ecommerce_api.repository.PagamentoRepository;
import com.ecommerce.ecommerce_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    @Transactional
    public Pagamento processarPagamento(UUID pedidoId, MetodoPagamento metodo) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPedido.PENDENTE){
            throw new RuntimeException("Apenas pedidos com status PENDENTE podem ser pagos. Status atual: " + pedido.getStatus());
        }
        if (pagamentoRepository.findByPedidoId(pedidoId).isPresent()) {
            throw new RuntimeException("Esse pedido ja tem pagamento");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setAmount(pedido.getTotalAmount());
        pagamento.setMetodo(metodo);
        pagamento.setStatus(StatusPagamento.APPROVED);
        pagamento.setPagoEm(LocalDateTime.now());

        pagamentoRepository.save(pagamento);
        pedidoService.atualizarStatus(pedidoId, StatusPedido.PAGO);
        return pagamento;
    }

    public Pagamento buscarPorPedido(UUID pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(()-> new RuntimeException("Pagamento não encontrado"));
    }
}
