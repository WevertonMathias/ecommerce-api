package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.NomePapel;
import com.ecommerce.ecommerce_api.entity.Papel;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.exception.RecursoNaoEncontradoException;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.PapelRepository;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PapelRepository papelRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void cadastrar_deveSalvarUsuario_quandoEmailNaoExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("weverton@teste.com");
        usuario.setSenhaHash("123456");

        Papel papelCliente = new Papel();
        papelCliente.setName(NomePapel.CLIENTE);

        when(usuarioRepository.existsByEmail("weverton@teste.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hash-criptografado");
        when(papelRepository.findByName(NomePapel.CLIENTE)).thenReturn(Optional.of(papelCliente));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrar(usuario, "123456");

        assertEquals("hash-criptografado", resultado.getSenhaHash());
        assertTrue(resultado.getPapeis().contains(papelCliente));
    }

    @Test
    void cadastrar_deveLancarExcecao_quandoEmailJaExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("weverton@teste.com");

        when(usuarioRepository.existsByEmail("weverton@teste.com")).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> {
            usuarioService.cadastrar(usuario, "123456");
        });

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void buscarPorId_deveLancarExcecao_quandoIdNaoExiste() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.buscarPorId(id);
        });
    }
}
