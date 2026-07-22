package com.gustavo.posto_combustivel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AbastecimentoRequestDTO(
        @Schema(type = "LocalDateTime", description = "Data e hora do abastecimento", example = "21/07/2026 18:30")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        @Schema(type = "BigDecimal", description = "Quantidade abastecida em litros", example = "25.50", minimum = "0.01")
        BigDecimal litros,
        @Schema(type = "Long", description = "Identificador da bomba utilizada", example = "1")
        Long bombaId
) {
}
