package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.ecommerce_api.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.mapper.UsuarioMapper;
import com.ecommerce.ecommerce_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto){
        Usuario entidade = usuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = usuarioService.cadastrar(entidade, dto.senha());
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(usuarioSalvo);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or @autorizacao.ehDono(#usuarioId, authentication)")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        Usuario entidade = usuarioService.buscarPorId(id);
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(entidade);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarAtivos() {
        List<Usuario> entidades = usuarioService.listarAtivos();
        List<UsuarioResponseDTO> listaDTO = entidades.stream()
                .map(usuarioMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or @autorizacao.ehDono(#usuarioId, authentication)")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@Valid @PathVariable UUID id, @RequestBody UsuarioRequestDTO dto) {
        Usuario entidades = usuarioMapper.toEntity(dto);
        Usuario usuarioAtualizado = usuarioService.atualizar(id, entidades);
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or @autorizacao.ehDono(#usuarioId, authentication)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
