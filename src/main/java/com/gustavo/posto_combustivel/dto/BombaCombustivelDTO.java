package com.gustavo.posto_combustivel.dto;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BombaCombustivelDTO {

    private Long id;
    private String nome;
    private TipoCombustivelDTO tipoCombustivel;
}
