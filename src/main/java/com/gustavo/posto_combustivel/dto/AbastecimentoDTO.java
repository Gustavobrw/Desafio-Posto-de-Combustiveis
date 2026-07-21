package com.gustavo.posto_combustivel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoDTO {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private BigDecimal litros;
    private BombaCombustivelDTO bomba;
}
