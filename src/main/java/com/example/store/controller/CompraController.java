package com.example.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.store.dtos.CompraRecordDto;
import com.example.store.models.CompraModel;
import com.example.store.models.JogoModel;
import com.example.store.repositories.CompraRepository;
import com.example.store.repositories.JogoRepository;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    CompraRepository compraRepository;

    @PostMapping
    public ResponseEntity<String> comprarJogo(@RequestBody @Valid CompraRecordDto compraDto) {
        try {
            Optional<JogoModel> jogoOpt = jogoRepository.findById(compraDto.getIdJogo());
            if (jogoOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");
            }

            JogoModel jogo = jogoOpt.get();
            if (jogo.getQtdEstoque() < compraDto.getQuantidade()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estoque insuficiente");
            }

            // Diminuir o estoque
            jogo.setQtdEstoque(jogo.getQtdEstoque() - compraDto.getQuantidade());
            jogoRepository.save(jogo);

            // Registrar a compra
            CompraModel compra = new CompraModel();
            compra.setNomeJogo(jogo.getNome());
            compra.setValorJogo(jogo.getValor()); // Certifique-se de que o modelo JogoModel possui o campo valor
            compra.setIdJogo(jogo.getIdJogo());
            compraRepository.save(compra);

            return ResponseEntity.status(HttpStatus.OK).body("Compra realizada com sucesso. Estoque restante: " + jogo.getQtdEstoque());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar compra");
        }
    }
}
