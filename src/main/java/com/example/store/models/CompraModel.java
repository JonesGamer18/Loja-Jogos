package com.example.store.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "COMPRAS")
public class CompraModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCompra;
    private String nomeJogo;
    private double valorJogo;
    private UUID idJogo;

    // Getters and setters
    public UUID getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(UUID idCompra) {
        this.idCompra = idCompra;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public double getValorJogo() {
        return valorJogo;
    }

    public void setValorJogo(double valorJogo) {
        this.valorJogo = valorJogo;
    }

    public UUID getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(UUID idJogo) {
        this.idJogo = idJogo;
    }
}
