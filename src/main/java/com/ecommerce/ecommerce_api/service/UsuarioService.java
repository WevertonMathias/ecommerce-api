package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.NomePapel;
import com.ecommerce.ecommerce_api.entity.Papel;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.repository.PapelRepository;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final PapelRepository papelRepository;

    @Transactional
    public Usuario cadastrar(Usuario usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new RuntimeException("Email já cadastrado!");
        }

        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        Papel papelCliente = papelRepository.findByName(NomePapel.CLIENTE)
                .orElseThrow(()-> new RuntimeException("Papel CLIENTE não encontrado!"));
        usuario.adicionarPapel(papelCliente);
        return usuarioRepository.save(usuario);
    }
}
