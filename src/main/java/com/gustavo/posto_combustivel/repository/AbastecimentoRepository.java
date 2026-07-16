package com.gustavo.posto_combustivel.repository;

import com.gustavo.posto_combustivel.entity.AbastecimentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbastecimentoRepository extends JpaRepository<AbastecimentoModel,Long> {
}
