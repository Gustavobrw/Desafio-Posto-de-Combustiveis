package com.gustavo.posto_combustivel.repository;

import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BombaCombustivelRepository extends JpaRepository<BombaCombustivelModel, Long> {
}
