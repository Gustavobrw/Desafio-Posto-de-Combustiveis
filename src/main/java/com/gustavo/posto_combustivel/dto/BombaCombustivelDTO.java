package com.gustavo.posto_combustivel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BombaCombustivelDTO {
    @Schema(type = "Long", description = "Codigo da bomba de combustível")
    private Long id;
    @Schema(type = "String", description = "Nome da bomba de combustível")
    private String nome;
    @Schema(type = "Tipo Combustivel", description = "Tipo do combustível da bomba")
    private TipoCombustivelDTO tipoCombustivel;
}
