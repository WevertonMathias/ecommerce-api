package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Categoria;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.CategoriaRepository;
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
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void criar_deveSalvarCategoria_quandoNomeNaoExiste(){
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.existsByNome("Eletrônicos")).thenReturn(false);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaService.criar(categoria);

        assertNotNull(resultado);
        assertEquals("Eletrônicos", resultado.getNome());

        verify(categoriaRepository, times(1)).existsByNome("Eletrônicos");
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void criar_deveLancarExcecao_quandoNomeJaExiste() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.existsByNome("Eletrônicos")).thenReturn(true);
        assertThrows(RegraDeNegocioException.class, () -> {
            categoriaService.criar(categoria);
        });

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoIdNaoExiste() {
        UUID id = UUID.randomUUID();
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, ()-> {
            categoriaService.buscarPorId(id);
        });
    }

}
