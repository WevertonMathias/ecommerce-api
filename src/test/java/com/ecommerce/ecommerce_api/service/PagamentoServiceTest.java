package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.*;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.PagamentoRepository;
import com.ecommerce.ecommerce_api.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void processarPagamento_deveAprovar_quandoPedidoPendenteESemPagamento() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTotalAmount(BigDecimal.valueOf(150));

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(pagamentoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.empty());
        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento resultado = pagamentoService.processarPagamento(pedidoId, MetodoPagamento.PIX);

        assertEquals(StatusPagamento.APPROVED, resultado.getStatus());
        assertEquals(BigDecimal.valueOf(150), resultado.getAmount());
        verify(pedidoService, times(1)).atualizarStatus(pedidoId, StatusPedido.PAGO);
    }

    @Test
    void processarPagamento_deveLancarExcecao_quandoPedidoNaoPendente() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setStatus(StatusPedido.PAGO);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        assertThrows(RegraDeNegocioException.class, () -> {
            pagamentoService.processarPagamento(pedidoId, MetodoPagamento.PIX);
        });

        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }

    @Test
    void processarPagamento_deveLancarExcecao_quandoJaExistePagamento() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setStatus(StatusPedido.PENDENTE);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(pagamentoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.of(new Pagamento()));

        assertThrows(RegraDeNegocioException.class, () -> {
            pagamentoService.processarPagamento(pedidoId, MetodoPagamento.PIX);
        });

        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }

    @Test
    void buscarPorPedido_deveLancarExcecao_quandoPagamentoNaoExiste() {
        UUID pedidoId = UUID.randomUUID();
        when(pagamentoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            pagamentoService.buscarPorPedido(pedidoId);
        });
    }
}
