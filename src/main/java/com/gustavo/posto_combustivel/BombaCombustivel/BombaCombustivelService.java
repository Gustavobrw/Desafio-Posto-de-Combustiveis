package com.gustavo.posto_combustivel.BombaCombustivel;

import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelModel;
import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BombaCombustivelService {

    private final BombaCombustivelRepository repository;
    private final BombaCombustivelMapper mapper;
    private final TipoCombustivelRepository tipoRepository;

    public List<BombaCombustivelDTO> listar(){
       List<BombaCombustivelModel> bomba = repository.findAll();
       return bomba.stream()
               .map(mapper::toDTO)
               .toList();
    }

    public BombaCombustivelDTO listarPorId(Long id){
        Optional<BombaCombustivelModel> bombaPorId = repository.findById(id);
            return bombaPorId.map(mapper::toDTO).orElseThrow(() -> new NullPointerException("Bomba de combustivel não encotrada"));
    }

    public BombaCombustivelDTO salvar(BombaCombustivelDTO bombaCombustivelDTO){
        TipoCombustivelModel tipo = tipoRepository.findById(bombaCombustivelDTO.getTipoCombustivel().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de combustivel não encontrado"));

        BombaCombustivelModel bomba = BombaCombustivelModel.builder()
                .nome(bombaCombustivelDTO.getNome())
                .tipoCombustivel(tipo)
                .build();

        repository.save(bomba);

        return mapper.toDTO(bomba);
    }

    public BombaCombustivelDTO alterar(Long id,BombaCombustivelDTO bombaCombustivelDTO){
        BombaCombustivelModel bombaAtt = mapper.toModel(bombaCombustivelDTO);
        bombaAtt.setId(id);
        repository.save(bombaAtt);
        return mapper.toDTO(bombaAtt);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
