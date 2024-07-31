package com.example.store.repositories;

import com.example.store.models.JogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JogoRepository extends JpaRepository<JogoModel, UUID> {
}
