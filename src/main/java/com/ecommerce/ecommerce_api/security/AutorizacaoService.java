package com.ecommerce.ecommerce_api.security;

import com.ecommerce.ecommerce_api.repository.PedidoRepository;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("autorizacao")
@RequiredArgsConstructor
public class AutorizacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public boolean ehDono(UUID usuarioId, Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .map(usuario -> usuario.getId().equals(usuarioId))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean ehDonoDoPedido(UUID pedidoId, Authentication authentication) {
        return pedidoRepository.findById(pedidoId)
                .map(pedido -> pedido.getUsuario().getEmail().equals(authentication.getName()))
                .orElse(false);
    }
}