package com.gustavo.posto_combustivel.mapper;

import com.gustavo.posto_combustivel.dto.AbastecimentoDTO;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.AbastecimentoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AbastecimentoMapper {
        private final BombaCombustivelMapper bombaMapper;
    public AbastecimentoModel toModel (AbastecimentoDTO abastecimentoDTO){
        BombaCombustivelModel bombaId = BombaCombustivelModel.builder()
                .id(abastecimentoDTO.getBomba().getId())
                .build();

       return  AbastecimentoModel.builder()
               .data(abastecimentoDTO.getData())
               .litros(abastecimentoDTO.getLitros())
               .bomba(bombaId)
               .build();
    }

    public AbastecimentoDTO toDTO (AbastecimentoModel abastecimentoModel){
        return AbastecimentoDTO.builder()
                .id(abastecimentoModel.getId())
                .data(abastecimentoModel.getData())
                .valorTotal(abastecimentoModel.getValorTotal())
                .litros(abastecimentoModel.getLitros())
                .bomba(bombaMapper.toDTO(abastecimentoModel.getBomba()))
                .build();
    }
}
