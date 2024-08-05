package com.example.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.store.models.CompraModel;
import java.util.UUID;

public interface CompraRepository extends JpaRepository<CompraModel, UUID> {
}
