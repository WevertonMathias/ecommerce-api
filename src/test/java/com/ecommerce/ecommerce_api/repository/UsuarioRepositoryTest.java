package com.ecommerce.ecommerce_api.repository;

import com.ecommerce.ecommerce_api.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarEBuscarUsuarioPorEmail() {
        // Cenário
        Usuario usuario = new Usuario();
        usuario.setNome("Teste User");
        usuario.setEmail("teste@email.com");
        usuario.setSenhaHash("hash123456");
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        // Ação
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("teste@email.com");

        // Verificação
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Teste User");
    }
}