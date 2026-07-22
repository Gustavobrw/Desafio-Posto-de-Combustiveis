package com.gustavo.posto_combustivel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BombaCombustivelRequestDTO(
        @Schema(type = "String", description = "Nome da bomba de combustível", example = "Bomba 01")
        String nome,
        @Schema(type = "Long", description = "Tipo do combustível da bomba", example = "1")
        Long tipoCombustivelId
) {
}
