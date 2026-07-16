package com.gustavo.posto_combustivel.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoCombustivelDTO {

private Long id;
private String nome;
private BigDecimal precoLitro;
}
