package com.gustavo.posto_combustivel.mapper;

import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import org.springframework.stereotype.Component;

@Component
public class TipoCombustivelMapper {

    public TipoCombustivelModel toModel(TipoCombustivelDTO tipoCombustivelDTO){
            return TipoCombustivelModel.builder()
                    .id(tipoCombustivelDTO.getId())
                    .nome(tipoCombustivelDTO.getNome())
                    .precoLitro(tipoCombustivelDTO.getPrecoLitro())
                    .build();
    }

    public TipoCombustivelDTO toDTO(TipoCombustivelModel tipoCombustivelModel){
        return TipoCombustivelDTO.builder()
                .id(tipoCombustivelModel.getId())
                .nome(tipoCombustivelModel.getNome())
                .precoLitro(tipoCombustivelModel.getPrecoLitro())
                .build();
    }
}
