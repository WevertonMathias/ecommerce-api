package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.varianteProduto.VarianteProdutoRequestDTO;
import com.ecommerce.ecommerce_api.dto.varianteProduto.VarianteProdutoResponseDTO;
import com.ecommerce.ecommerce_api.entity.VarianteProduto;
import com.ecommerce.ecommerce_api.mapper.VarianteProdutoMapper;
import com.ecommerce.ecommerce_api.service.VarianteProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/variantes")
@RequiredArgsConstructor
public class VarianteProdutoController {

    private final VarianteProdutoService varianteProdutoService;
    private final VarianteProdutoMapper varianteProdutoMapper;

    @PostMapping
    public ResponseEntity<VarianteProdutoResponseDTO> criar(@RequestBody VarianteProdutoRequestDTO dto){
        VarianteProduto entidades = varianteProdutoMapper.toEntity(dto);
        VarianteProduto varianteSalvo = varianteProdutoService.criar(entidades);
        VarianteProdutoResponseDTO responseDTO = varianteProdutoMapper.toResponseDTO(varianteSalvo);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VarianteProdutoResponseDTO> buscarPorId(@PathVariable UUID id){
        VarianteProduto variante = varianteProdutoService.buscarPorId(id);
        VarianteProdutoResponseDTO responseDTO = varianteProdutoMapper.toResponseDTO(variante);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<VarianteProdutoResponseDTO>> listarTodos(@RequestParam UUID produtoId){
        List<VarianteProduto> entidades = varianteProdutoService.listarPorProduto(produtoId);
        List<VarianteProdutoResponseDTO> listaDTO = entidades.stream()
                .map(varianteProdutoMapper::toResponseDTO).toList();
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VarianteProdutoResponseDTO> atualizar(@PathVariable UUID id, @RequestBody VarianteProdutoRequestDTO dto){
        VarianteProduto entidades = varianteProdutoMapper.toEntity(dto);
        VarianteProduto varianteAtualizada = varianteProdutoService.atualizar(id, entidades);
        VarianteProdutoResponseDTO responseDTO = varianteProdutoMapper.toResponseDTO(varianteAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<VarianteProdutoResponseDTO> diminuirEstoque(
            @PathVariable UUID id,
            @RequestParam int quantidade) {
        VarianteProduto variante = varianteProdutoService.diminuirEstoque(id, quantidade);
        VarianteProdutoResponseDTO responseDTO = varianteProdutoMapper.toResponseDTO(variante);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        varianteProdutoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
