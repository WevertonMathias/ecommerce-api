package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Categoria;
import com.ecommerce.ecommerce_api.entity.Produto;
import com.ecommerce.ecommerce_api.repository.CategoriaRepository;
import com.ecommerce.ecommerce_api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Produto criar(Produto produto){
        Categoria categoriaEncontrada = categoriaRepository.findById(produto.getCategoria().getId())
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada!"));

        produto.setCategoria(categoriaEncontrada);

        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(UUID id){
        return produtoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }

    public List<Produto> listarAtivos(){
        return produtoRepository.findByAtivoTrue();
    }

    @Transactional
    public Produto atualizarProduto(UUID id, Produto produtoNovo){
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produtoNovo.getNome());
        produtoExistente.setAtivo(produtoNovo.isAtivo());
        produtoExistente.setDescricao(produtoNovo.getDescricao());

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void inativar(UUID id){
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        produtoExistente.setAtivo(false);
        produtoRepository.save(produtoExistente);
    }
}
