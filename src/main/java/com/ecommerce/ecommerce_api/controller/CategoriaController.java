package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.categoria.CategoriaRequestDTO;
import com.ecommerce.ecommerce_api.dto.categoria.CategoriaResponseDTO;
import com.ecommerce.ecommerce_api.entity.Categoria;
import com.ecommerce.ecommerce_api.mapper.CategoriaMapper;
import com.ecommerce.ecommerce_api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody CategoriaRequestDTO dto){
        Categoria entidade = categoriaMapper.toEntity(dto);
        Categoria categoriaSalva = categoriaService.criar(entidade);
        CategoriaResponseDTO responseDTO = categoriaMapper.toResponseDTO(categoriaSalva);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        CategoriaResponseDTO responseDTO = categoriaMapper.toResponseDTO(categoria);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        List<Categoria> entidades = categoriaService.listarTodas();
        List<CategoriaResponseDTO> listaDTO = entidades.stream()
                .map(categoriaMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable UUID id, @RequestBody CategoriaRequestDTO dto) {
        Categoria entidades = categoriaMapper.toEntity(dto);
        Categoria categoriaAtualizada = categoriaService.atualizar(id, entidades);
        CategoriaResponseDTO responseDTO = categoriaMapper.toResponseDTO(categoriaAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
