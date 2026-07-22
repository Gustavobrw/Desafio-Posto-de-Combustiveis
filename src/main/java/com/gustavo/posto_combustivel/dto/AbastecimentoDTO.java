package com.gustavo.posto_combustivel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoDTO {
    @Schema(type = "Long", description = "Codigo do abastecimento")
    private Long id;
    @Schema(type = "LocalDateTime", description = "Data do abastecimento")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;
    @Schema(type = "BigDecimal", description = "Valor total do abastecimento")
    private BigDecimal valorTotal;
    @Schema(type = "BigDecimal", description = "Litros abastecidos")
    private BigDecimal litros;
    @Schema(type = "Bomba Combustivel", description = "Bomba de combustível utilizada")
    private BombaCombustivelDTO bomba;
}
