package com.example.store.dtos;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record JogoRecordDto(
    @NotBlank String nome,
    @NotBlank String genero,
    @NotBlank String plataforma,
    @NotNull @PositiveOrZero int qtdEstoque,
    @NotNull double valor
) {
}
