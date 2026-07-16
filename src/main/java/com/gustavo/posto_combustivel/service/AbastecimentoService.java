package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.AbastecimentoDTO;
import com.gustavo.posto_combustivel.dto.AbastecimentoRequestDTO;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.AbastecimentoModel;
import com.gustavo.posto_combustivel.mapper.AbastecimentoMapper;
import com.gustavo.posto_combustivel.repository.AbastecimentoRepository;
import com.gustavo.posto_combustivel.repository.BombaCombustivelRepository;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
        AbastecimentoModel abastecimento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Abastecimento com o id "+ id + " não encontrado"
                ));

        return mapper.toDTO(abastecimento);
    }

    public AbastecimentoDTO salvar(AbastecimentoRequestDTO request){
        //Busco a minha bomba
       if (request.bombaId() == null ) {
            throw new BusinessException("A bomba de combustível deve ser informada para realizar o abastecimento.");
        }

       if(request.litros() == null || request.litros().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("A quantidade de litros deve ser maior que zero.");
        }

       BombaCombustivelModel bomba = bombaCombustivelRepository.findById(request.bombaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bomba de combustível não encontrada"
                ));

        //Calculo valor total
        BigDecimal precoLitro = bomba.getTipoCombustivel().getPrecoLitro();
        BigDecimal valorTotal = precoLitro.multiply(request.litros());

        AbastecimentoModel abastecimento = AbastecimentoModel.builder()
                .data(request.data())
                .litros(request.litros())
                .valorTotal(valorTotal)
                .bomba(bomba)
                .build();

        AbastecimentoModel abastecimentoSalvo = repository.save(abastecimento);

        return mapper.toDTO(abastecimentoSalvo);
    }

    public AbastecimentoDTO alterar(Long id, AbastecimentoRequestDTO request){
        AbastecimentoModel abastecimentoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Abastecimento com o id "+ id + " não encontrado"
                ));

        if(request.litros() != null ){
            throw new BusinessException("Não é permitido alterar a quantidade de litros do abastecimento.");
        }

        if(request.bombaId() != null) {
          throw new BusinessException("Não é permitido alterar a bomba de combustível do abastecimento.");
        }

        abastecimentoExistente.setData(request.data() != null ? request.data() : abastecimentoExistente.getData());

        AbastecimentoModel abastecimentoAtualizado = repository.save(abastecimentoExistente);

        return mapper.toDTO(abastecimentoAtualizado);
    }

    public void delete(Long id){
        AbastecimentoModel abastecimentoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Abastecimento não encontrado"
                ));

        repository.delete(abastecimentoExistente);
    }
}
