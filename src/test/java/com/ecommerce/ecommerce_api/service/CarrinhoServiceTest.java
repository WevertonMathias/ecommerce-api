package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Carrinho;
import com.ecommerce.ecommerce_api.entity.ItemCarrinho;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.repository.CarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.ItemCarrinhoRepository;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
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
public class CarrinhoServiceTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Mock
    private VarianteProdutoRepository varianteProdutoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Test
    void buscarOuCriarCarrinho_deveCriarCarrinhoNovo_quandoUsuarioNaoTemCarrinho() {
        UUID usuarioId = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.empty());
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(carrinhoRepository.save(any(Carrinho.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Carrinho resultado = carrinhoService.buscarOuCriarCarrinho(usuarioId);

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        verify(carrinhoRepository, times(1)).save(any(Carrinho.class));
    }

    @Test
    void buscarOuCriarCarrinho_deveLancarExcecao_quandoUsuarioNaoExiste() {
        UUID usuarioId = UUID.randomUUID();

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.empty());
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            carrinhoService.buscarOuCriarCarrinho(usuarioId);
        });
    }

    @Test
    void adicionarItem_deveSomarQuantidade_quandoItemJaExisteNoCarrinho() {
        UUID usuarioId = UUID.randomUUID();
        UUID varianteId = UUID.randomUUID();

        Carrinho carrinho = new Carrinho();
        carrinho.setId(UUID.randomUUID());

        VarianteProduto variante = new VarianteProduto();
        variante.setId(varianteId);

        ItemCarrinho itemExistente = new ItemCarrinho();
        itemExistente.setQuantidade(2);

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrinho));
        when(varianteProdutoRepository.findById(varianteId)).thenReturn(Optional.of(variante));
        when(itemCarrinhoRepository.findByCarrinhoIdAndVarianteProdutoId(carrinho.getId(), varianteId))
                .thenReturn(Optional.of(itemExistente));

        carrinhoService.adicionarItem(usuarioId, varianteId, 3);

        assertEquals(5, itemExistente.getQuantidade());
        verify(itemCarrinhoRepository, times(1)).save(itemExistente);
    }

    @Test
    void removerItem_deveLancarExcecao_quandoItemNaoExiste() {
        UUID usuarioId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        Carrinho carrinho = new Carrinho();
        carrinho.setId(UUID.randomUUID());

        when(carrinhoRepository.findByUsuarioId(usuarioId)).thenReturn(Optional.of(carrinho));
        when(itemCarrinhoRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            carrinhoService.removerItem(usuarioId, itemId);
        });
    }
}
