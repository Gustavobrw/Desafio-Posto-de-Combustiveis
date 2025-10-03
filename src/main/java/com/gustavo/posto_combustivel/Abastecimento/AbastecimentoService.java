package com.gustavo.posto_combustivel.Abastecimento;

import com.gustavo.posto_combustivel.BombaCombustivel.BombaCombustivelModel;
import com.gustavo.posto_combustivel.BombaCombustivel.BombaCombustivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {
    private final AbastecimentoRepository repository;
    private final AbastecimentoMapper mapper;
    private final BombaCombustivelRepository bombaCombustivelRepository;

    public List<AbastecimentoDTO> listar(){
        List<AbastecimentoModel> abastecimentos = repository.findAll();
        return abastecimentos.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public AbastecimentoDTO listarPorId(Long id){
        Optional<AbastecimentoModel> abastecimento = repository.findById(id);
        if(abastecimento.isPresent()){
            return mapper.toDTO(abastecimento.orElse(null));
        }
        return null;
    }

    public AbastecimentoDTO salvar(AbastecimentoDTO abastecimentoDTO){
        //Busco a minha bomba
        BombaCombustivelModel bomba = bombaCombustivelRepository.findById(abastecimentoDTO.getBomba().getId())
                .orElseThrow(() -> new RuntimeException ("Bomba de Combustivel não encontrada"));

        //Calculo valor total
        BigDecimal precoLitro = bomba.getTipoCombustivel().getPrecoLitro();
        BigDecimal valorTotal = precoLitro.multiply(abastecimentoDTO.getLitros());

        AbastecimentoModel abastecimento = AbastecimentoModel.builder()
                .data(abastecimentoDTO.getData())
                .litros(abastecimentoDTO.getLitros())
                .valorTotal(valorTotal)
                .bomba(bomba)
                .build();

        AbastecimentoModel abastecimentoSalvo = repository.save(abastecimento);

        return mapper.toDTO(abastecimentoSalvo);
    }

    public AbastecimentoDTO alterar(Long id, AbastecimentoDTO abastecimentoDTO){
        AbastecimentoModel abastecimentoAtt = mapper.toModel(abastecimentoDTO);
        abastecimentoAtt.setId(id);
        repository.save(abastecimentoAtt);

        return mapper.toDTO(abastecimentoAtt);

    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
