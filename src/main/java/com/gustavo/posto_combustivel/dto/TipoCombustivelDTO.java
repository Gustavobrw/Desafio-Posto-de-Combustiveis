package com.gustavo.posto_combustivel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoCombustivelDTO {
    @Schema(type = "Long", description = "Codigo do tipo combustível")
    private Long id;
    @Schema(type = "String", description = "Nome do tipo combustível")
    private String nome;
    @Schema(type = "BigDecimal", description = "Preco do litro do tipo combustível")
    private BigDecimal precoLitro;
}
