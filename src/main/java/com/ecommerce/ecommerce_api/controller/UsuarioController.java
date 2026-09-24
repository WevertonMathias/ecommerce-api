package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.usuario.UsuarioRequestDTO;
import com.ecommerce.ecommerce_api.dto.usuario.UsuarioResponseDTO;
import com.ecommerce.ecommerce_api.entity.Usuario;
import com.ecommerce.ecommerce_api.mapper.UsuarioMapper;
import com.ecommerce.ecommerce_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioRequestDTO dto){
        Usuario entidade = usuarioMapper.toEntity(dto);
        Usuario usuarioSalvo = usuarioService.cadastrar(entidade);
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(usuarioSalvo);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        Usuario entidade = usuarioService.buscarPorId(id);
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(entidade);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarAtivos() {
        List<Usuario> entidades = usuarioService.listarAtivos();
        List<UsuarioResponseDTO> listaDTO = entidades.stream()
                .map(usuarioMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable UUID id, @RequestBody UsuarioRequestDTO dto) {
        Usuario entidades = usuarioMapper.toEntity(dto);
        Usuario usuarioAtualizado = usuarioService.atualizar(id, entidades);
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(usuarioAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
