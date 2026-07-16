package com.gustavo.posto_combustivel.repository;

import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombustivelRepository extends JpaRepository<TipoCombustivelModel,Long> {
}
