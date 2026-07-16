package com.gustavo.posto_combustivel.mapper;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
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
