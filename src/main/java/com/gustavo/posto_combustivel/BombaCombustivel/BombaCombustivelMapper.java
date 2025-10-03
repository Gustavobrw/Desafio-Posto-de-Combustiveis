package com.gustavo.posto_combustivel.BombaCombustivel;

import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelMapper;
import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BombaCombustivelMapper {
    private final TipoCombustivelMapper tipoMapper;

    public BombaCombustivelModel toModel (BombaCombustivelDTO bombaCombustivelDTO){
        TipoCombustivelModel tipoId = TipoCombustivelModel.builder()
                .id(bombaCombustivelDTO.getTipoCombustivel().getId())
                .build();


        return BombaCombustivelModel.builder()
                .nome(bombaCombustivelDTO.getNome())
                .tipoCombustivel(tipoId)
                .build();
    }

    public BombaCombustivelDTO toDTO (BombaCombustivelModel bombaCombustivelModel){
        return BombaCombustivelDTO.builder()
                .id(bombaCombustivelModel.getId())
                .nome(bombaCombustivelModel.getNome())
                .tipoCombustivel(tipoMapper.toDTO(bombaCombustivelModel.getTipoCombustivel()))
                .build();
    }
}
