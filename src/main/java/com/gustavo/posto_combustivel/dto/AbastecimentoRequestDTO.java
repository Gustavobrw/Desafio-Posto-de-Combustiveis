package com.gustavo.posto_combustivel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AbastecimentoRequestDTO(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        BigDecimal litros,
        Long bombaId
) {
}
