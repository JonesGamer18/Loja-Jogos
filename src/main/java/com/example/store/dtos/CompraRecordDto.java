package com.example.store.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CompraRecordDto {

    @NotNull
    private UUID idJogo;

    @NotNull
    private int quantidade;

    public UUID getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(UUID idJogo) {
        this.idJogo = idJogo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
