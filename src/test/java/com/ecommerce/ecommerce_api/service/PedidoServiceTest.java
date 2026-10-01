package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.*;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.CarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {


    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private VarianteProdutoService varianteProdutoService;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void criarAPartirDoCarrinho_deveLancarExcecao_quandoCarrinhoVazio() {
        UUID usuarioId = UUID.randomUUID();
        Carrinho carrinho = new Carrinho();
        carrinho.setItens(new java.util.ArrayList<>());

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrinho));

        assertThrows(RegraDeNegocioException.class, () -> {
            pedidoService.criarAPartirDoCarrinho(usuarioId, new Endereco());
        });

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void criarAPartirDoCarrinho_deveCriarPedido_quandoCarrinhoTemItens() {
        UUID usuarioId = UUID.randomUUID();

        Usuario usuario = new Usuario();
        VarianteProduto variante = new VarianteProduto();
        variante.setId(UUID.randomUUID());
        variante.setPreco(BigDecimal.valueOf(50));

        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setVarianteProduto(variante);
        itemCarrinho.setQuantidade(2);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setItens(new java.util.ArrayList<>(java.util.List.of(itemCarrinho)));

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrinho));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = pedidoService.criarAPartirDoCarrinho(usuarioId, new Endereco());

        assertEquals(StatusPedido.PENDENTE, resultado.getStatus());
        assertEquals(BigDecimal.valueOf(100), resultado.getTotalAmount());
        verify(varianteProdutoService, times(1)).diminuirEstoque(variante.getId(), 2);
    }

    @Test
    void atualizarStatus_devePermitirTransicao_dePendenteParaPago() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.PENDENTE);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.atualizarStatus(pedidoId, StatusPedido.PAGO);

        assertEquals(StatusPedido.PAGO, resultado.getStatus());
    }

    @Test
    void atualizarStatus_deveLancarExcecao_quandoTransicaoInvalida() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.ENTREGUE);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        assertThrows(RegraDeNegocioException.class, () -> {
            pedidoService.atualizarStatus(pedidoId, StatusPedido.PAGO);
        });

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}
