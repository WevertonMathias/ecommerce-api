package com.ecommerce.ecommerce_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> capturar(RecursoNaoEncontradoException ex){
        ErroResponseDTO erroDTO = new ErroResponseDTO(
                LocalDateTime.now(),
                404,
                "Não encontrado",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDTO> capturar(RegraDeNegocioException ex){
        ErroResponseDTO erroDTO = new ErroResponseDTO(
                LocalDateTime.now(),
                400,
                "Regra de negocio violada",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> capturar(MethodArgumentNotValidException ex){
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErroResponseDTO erroDTO = new ErroResponseDTO(
                LocalDateTime.now(),
                400,
                "Dados inválidos",
                mensagem
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
    }
}