package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.auth.LoginRequestDTO;
import com.ecommerce.ecommerce_api.dto.auth.LoginResponseDTO;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.exception.RegraDeNegocioException;
import com.ecommerce.ecommerce_api.repository.UsuarioRepository;
import com.ecommerce.ecommerce_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RegraDeNegocioException("Email ou senha inválidos"));
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenhaHash())){
            throw new RegraDeNegocioException("Email ou senha inválidos");
        }
        List<String> roles = usuario.getPapeis().stream()
                .map(papel -> papel.getName().name())
                .toList();
        String token = jwtService.gerarToken(usuario.getEmail(), roles);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
