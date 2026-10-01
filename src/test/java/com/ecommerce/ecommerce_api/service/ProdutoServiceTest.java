package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Categoria;
import com.ecommerce.ecommerce_api.entity.Produto;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.repository.CategoriaRepository;
import com.ecommerce.ecommerce_api.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void criar_deveSalvarProduto_quandoCategoriaExiste() {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome("Eletrônicos");

        Produto produto = new Produto();
        produto.setNome("Camiseta");
        produto.setCategoria(categoria);

        when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto resultado = produtoService.criar(produto, categoria.getId());

        assertNotNull(resultado);
        assertEquals("Camiseta", resultado.getNome());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void criar_deveLancarExcecao_quandoCategoriaNaoExiste() {
        Produto produto = new Produto();
        produto.setNome("Camiseta");
        UUID categoriaId = UUID.randomUUID();

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.criar(produto, categoriaId);
        });

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoIdNaoExiste() {
        UUID id = UUID.randomUUID();
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            produtoService.buscarPorId(id);
        });
    }
}
