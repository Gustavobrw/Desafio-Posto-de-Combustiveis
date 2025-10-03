package com.gustavo.posto_combustivel.BombaCombustivel;

import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelDTO;
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
