package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Produto;
import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.ProdutoRepository;
import com.ecommerce.ecommerce_api.repository.VarianteProdutoRepository;
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
public class VarianteProdutoServiceTest {

    @Mock
    private VarianteProdutoRepository varianteProdutoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private VarianteProdutoService varianteProdutoService;

    @Test
    void criar_deveSalvarVariante_quandoSkuNaoExisteEProdutoExiste() {
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());

        VarianteProduto variante = new VarianteProduto();
        variante.setSku("CAM-AZ-M");
        variante.setProduto(produto);

        when(varianteProdutoRepository.existsBySku("CAM-AZ-M")).thenReturn(false);
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(varianteProdutoRepository.save(variante)).thenReturn(variante);

        VarianteProduto resultado = varianteProdutoService.criar(variante, produto.getId());

        assertNotNull(resultado);
        assertEquals("CAM-AZ-M", resultado.getSku());
        verify(varianteProdutoRepository, times(1)).save(variante);
    }

    @Test
    void criar_deveLancarExcecao_quandoSkuJaExiste() {
        VarianteProduto variante = new VarianteProduto();
        variante.setSku("CAM-AZ-M");
        UUID produtoId = UUID.randomUUID();

        when(varianteProdutoRepository.existsBySku("CAM-AZ-M")).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> {
            varianteProdutoService.criar(variante, produtoId);
        });

        verify(produtoRepository, never()).findById(any(UUID.class));
        verify(varianteProdutoRepository, never()).save(any(VarianteProduto.class));
    }

    @Test
    void diminuirEstoque_deveReduzirQuantidade_quandoEstoqueSuficiente() {
        VarianteProduto variante = new VarianteProduto();
        variante.setId(UUID.randomUUID());
        variante.setQuantidadeEstoque(10);

        when(varianteProdutoRepository.findById(variante.getId())).thenReturn(Optional.of(variante));
        when(varianteProdutoRepository.save(variante)).thenReturn(variante);

        VarianteProduto resultado = varianteProdutoService.diminuirEstoque(variante.getId(), 3);

        assertEquals(7, resultado.getQuantidadeEstoque());
    }

    @Test
    void diminuirEstoque_deveLancarExcecao_quandoEstoqueInsuficiente() {
        VarianteProduto variante = new VarianteProduto();
        variante.setId(UUID.randomUUID());
        variante.setQuantidadeEstoque(5);

        when(varianteProdutoRepository.findById(variante.getId())).thenReturn(Optional.of(variante));

        assertThrows(RegraDeNegocioException.class, () -> {
            varianteProdutoService.diminuirEstoque(variante.getId(), 999);
        });

        verify(varianteProdutoRepository, never()).save(any(VarianteProduto.class));
    }
}
