package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Produto;
import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import com.ecommerce.ecommerce_api.repository.ProdutoRepository;
import com.ecommerce.ecommerce_api.repository.VarianteProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VarianteProdutoService {

    private final VarianteProdutoRepository varianteProdutoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public VarianteProduto criar(VarianteProduto variante){
        if (varianteProdutoRepository.existsBySku(variante.getSku())){
            throw new RuntimeException("Sku já cadastrado");
        }
        Produto produtoEncontrado = produtoRepository.findById(variante.getProduto().getId())
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        variante.setProduto(produtoEncontrado);
        return varianteProdutoRepository.save(variante);
    }

    public VarianteProduto buscarPorId(UUID id){
        return varianteProdutoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Variante de produto não encontrado"));
    }

    public List<VarianteProduto> listarPorProduto(UUID produtoId) {
         return varianteProdutoRepository.findByProdutoId(produtoId);
    }

    @Transactional
    public VarianteProduto atualizar(UUID id, VarianteProduto dadosNovos) {
        VarianteProduto varianteExistente = varianteProdutoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Variante não encontrada"));

        varianteExistente.setTamanho(dadosNovos.getTamanho());
        varianteExistente.setCor(dadosNovos.getCor());
        varianteExistente.setPreco(dadosNovos.getPreco());
        varianteExistente.setAtivo(dadosNovos.isAtivo());

        return varianteProdutoRepository.save(varianteExistente);
    }

    @Transactional
    public VarianteProduto diminuirEstoque(UUID id, int quantidade) {
        VarianteProduto variante = varianteProdutoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Variante não encontrada"));

        if (variante.getQuantidadeEstoque()< quantidade){
            throw new RuntimeException("Estoque insuficiente");
        }

        variante.setQuantidadeEstoque(variante.getQuantidadeEstoque() - quantidade);

        return varianteProdutoRepository.save(variante);
    }


}
