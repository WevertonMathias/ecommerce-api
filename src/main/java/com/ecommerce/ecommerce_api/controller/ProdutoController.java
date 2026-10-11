package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.produto.ProdutoRequestDTO;
import com.ecommerce.ecommerce_api.dto.produto.ProdutoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Produto;
import com.ecommerce.ecommerce_api.mapper.ProdutoMapper;
import com.ecommerce.ecommerce_api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        Produto entidades = produtoMapper.toEntity(dto);
        Produto produtoSalvo = produtoService.criar(entidades, dto.categoriaId());
        ProdutoResponseDTO responseDTO = produtoMapper.toResponseDTO(produtoSalvo);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable UUID id) {
        Produto produto = produtoService.buscarPorId(id);
        ProdutoResponseDTO responseDTO = produtoMapper.toResponseDTO(produto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<Produto> entidades = produtoService.listarAtivos();
        List<ProdutoResponseDTO> listaDTO = entidades.stream()
                .map(produtoMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@Valid @PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {
        Produto entidades = produtoMapper.toEntity(dto);
        Produto produtoAtualizado = produtoService.atualizarProduto(id, entidades);
        ProdutoResponseDTO responseDTO = produtoMapper.toResponseDTO(produtoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        produtoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
