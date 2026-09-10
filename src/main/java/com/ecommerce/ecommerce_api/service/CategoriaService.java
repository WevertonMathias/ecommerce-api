package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.entity.Categoria;
import com.ecommerce.ecommerce_api.repository.CategoriaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria criar(Categoria categoria){
        if (categoriaRepository.existsByNome(categoria.getNome())){
            throw new RuntimeException("Nome já cadastrado!");
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(UUID id){
        return categoriaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
    }

    public List<Categoria> listarTodas(){
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria atualizar(UUID id, Categoria dadosNovos){
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
        categoriaExistente.setNome(dadosNovos.getNome());
        categoriaExistente.setDescricao(dadosNovos.getDescricao());

        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public void deletar(UUID id){
        categoriaRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
        categoriaRepository.deleteById(id);
    }
}
