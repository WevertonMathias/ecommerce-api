package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.adicionarItem.AdicionarItemRequestDTO;
import com.ecommerce.ecommerce_api.dto.carrinho.CarrinhoResponseDTO;
import com.ecommerce.ecommerce_api.entity.Carrinho;
import com.ecommerce.ecommerce_api.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carrinhos")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarrinhoResponseDTO> buscarPorUsuario(@PathVariable UUID usuarioId){
        Carrinho carrinho = carrinhoService.buscarPorUsuario(usuarioId);
        CarrinhoResponseDTO responseDTO = carrinhoService.toResponseDTO(carrinho);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{usuarioId}/itens")
    public ResponseEntity<CarrinhoResponseDTO> adicionarItem(@PathVariable UUID usuarioId, @RequestBody AdicionarItemRequestDTO dto){
        Carrinho carrinho = carrinhoService.adicionarItem(usuarioId, dto.varianteProdutoId(), dto.quantidade());
        CarrinhoResponseDTO responseDTO = carrinhoService.toResponseDTO(carrinho);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{usuarioId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable UUID usuarioId, @PathVariable UUID itemId) {
        carrinhoService.removerItem(usuarioId, itemId);
        return ResponseEntity.noContent().build();
    }
}
