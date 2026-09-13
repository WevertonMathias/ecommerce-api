package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.*;
import com.ecommerce.ecommerce_api.repository.CarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.PedidoRepository;
import com.ecommerce.ecommerce_api.repository.VarianteProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final VarianteProdutoRepository varianteProdutoRepository;
    private final VarianteProdutoService varianteProdutoService;

    @Transactional
    public Pedido criarAPartirDoCarrinho(UUID usuarioId, Endereco endereco) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(()-> new RuntimeException("Usuario não encontrado"));

        if (carrinho.getItens().isEmpty()){
            throw new RuntimeException("Carrinho esta vazio");
        }

        Pedido pedido= new Pedido();
        pedido.setUsuario(carrinho.getUsuario());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setEndereco(endereco);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ItemCarrinho item : carrinho.getItens()){
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setVarianteProduto(item.getVarianteProduto());
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(item.getVarianteProduto().getPreco());
            itemPedido.setSubTotal(itemPedido.getPrecoUnitario()
                    .multiply(BigDecimal.valueOf(item.getQuantidade())));
            pedido.adicionarItem(itemPedido);
            varianteProdutoService.diminuirEstoque(
                    item.getVarianteProduto().getId(),
                    item.getQuantidade()

            );
            totalAmount = totalAmount.add(itemPedido.getSubTotal());
        }
        pedido.setTotalAmount(totalAmount);
        pedidoRepository.save(pedido);
        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);

        return pedido;
    }

    public Pedido buscarPorId(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado para o ID: " + id));
    }

    public List<Pedido> listarPorUsuario(UUID usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByCriadoEmDesc(usuarioId);
    }

    @Transactional
    public Pedido atualizarStatus(UUID pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado para o ID: " + pedidoId));

        StatusPedido statusAtual = pedido.getStatus();

        switch (statusAtual) {
            case PENDENTE:
                if (novoStatus != StatusPedido.PAGO && novoStatus != StatusPedido.CANCELADO) {
                    throw new RuntimeException("Pedido PENDENTE só pode virar PAGO ou CANCELADO");
                }
                break;
            case PAGO:
                if (novoStatus != StatusPedido.ENVIADO && novoStatus != StatusPedido.CANCELADO) {
                    throw new RuntimeException("Pedido PAGO só pode virar ENVIADO ou CANCELADO");
                }
                break;
            case ENVIADO:
                if (novoStatus != StatusPedido.ENTREGUE) {
                    throw new RuntimeException("Pedido ENVIADO só pode virar ENTREGUE");
                }
                break;
            case ENTREGUE:
            case CANCELADO:
                throw new RuntimeException("Pedido em status final não pode mudar de status");
        }

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
}
