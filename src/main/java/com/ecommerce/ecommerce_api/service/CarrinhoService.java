package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.carrinho.CarrinhoResponseDTO;
import com.ecommerce.ecommerce_api.dto.itemCarrinho.ItemCarrinhoResponseDTO;
import com.ecommerce.ecommerce_api.entity.*;
import com.ecommerce.ecommerce_api.repository.CarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.ItemCarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
import com.ecommerce.ecommerce_api.repository.VarianteProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final VarianteProdutoRepository varianteProdutoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Carrinho buscarOuCriarCarrinho(UUID usuarioId) {
        Optional<Carrinho> carrinhoExistente = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinhoExistente.isPresent()){
            return carrinhoExistente.get();
        }
        Usuario usuarioEncontrado = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Carrinho novoCarrinho = new Carrinho();
        novoCarrinho.setUsuario(usuarioEncontrado);

        return carrinhoRepository.save(novoCarrinho);
    }

    @Transactional
    public Carrinho adicionarItem(UUID usuarioId, UUID varianteProdutoId, int quantidade) {
        Carrinho carrinho = buscarOuCriarCarrinho(usuarioId);
        VarianteProduto varianteExistente = varianteProdutoRepository.findById(varianteProdutoId)
                .orElseThrow(()-> new RuntimeException("Variante não encontrada"));

        Optional<ItemCarrinho> itemExistente = itemCarrinhoRepository.findByCarrinhoIdAndVarianteProdutoId(carrinho.getId()
                , varianteProdutoId);
        if (itemExistente.isPresent()){
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
            itemCarrinhoRepository.save(item);
        }else{
            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setCarrinho(carrinho);
            itemCarrinho.setVarianteProduto(varianteExistente);
            itemCarrinho.setQuantidade(quantidade);
            carrinho.adicionarItem(itemCarrinho);
            itemCarrinhoRepository.save(itemCarrinho);
        }
        return carrinho;
    }

    @Transactional
    public Carrinho removerItem(UUID usuarioId, UUID itemCarrinhoId) {
        Carrinho carrinho = buscarOuCriarCarrinho(usuarioId);
        ItemCarrinho item = itemCarrinhoRepository.findById(itemCarrinhoId)
                .orElseThrow(()-> new RuntimeException("Item não encontrado"));
        carrinho.removerItem(item);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho buscarPorUsuario(UUID usuarioId) {
        return buscarOuCriarCarrinho(usuarioId);
    }

    public CarrinhoResponseDTO toResponseDTO(Carrinho carrinho){
        List<ItemCarrinhoResponseDTO> itensResponse = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemCarrinho item : carrinho.getItens()){
            BigDecimal subTotal = item.getVarianteProduto().getPreco()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));

            ItemCarrinhoResponseDTO itemDTO = new ItemCarrinhoResponseDTO(
                    item.getId(),
                    item.getVarianteProduto().getProduto().getNome(),
                    item.getQuantidade(),
                    item.getVarianteProduto().getPreco(),
                    subTotal
            );
            itensResponse.add(itemDTO);

            valorTotal = valorTotal.add(itemDTO.subtotal());
        }
        return  new CarrinhoResponseDTO(carrinho.getId(), itensResponse, valorTotal);
    }

}
