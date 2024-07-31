package com.example.store.dtos;

import jakarta.validation.constraints.NotBlank;

public record JogoRecordDto(@NotBlank String nome, @NotBlank String genero, @NotBlank String plataforma,@NotBlank int qtd_estoque) {
}