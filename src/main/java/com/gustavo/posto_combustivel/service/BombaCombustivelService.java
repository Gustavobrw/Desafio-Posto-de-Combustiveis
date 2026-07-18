package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.dto.BombaCombustivelRequestDTO;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import com.gustavo.posto_combustivel.mapper.BombaCombustivelMapper;
import com.gustavo.posto_combustivel.repository.BombaCombustivelRepository;
import com.gustavo.posto_combustivel.repository.TipoCombustivelRepository;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        BombaCombustivelModel bomba = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba de combustivel não encontrada"));
        return mapper.toDTO(bomba);
    }

    public BombaCombustivelDTO salvar(BombaCombustivelRequestDTO request){
        if(request.nome() == null || request.nome().isEmpty()){
            throw new BusinessException("Nome da bomba de combustivel não pode ser nulo ou vazio");
        }
        if(request.tipoCombustivelId() == null ){
            throw new BusinessException("Tipo de combustivel não pode ser nulo");
        }
        TipoCombustivelModel tipoCombustivel = tipoRepository.findById(request.tipoCombustivelId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de combustivel não encontrado"));

        BombaCombustivelModel bomba = BombaCombustivelModel
                .builder()
                .nome(request.nome())
                .tipoCombustivel(tipoCombustivel)
                .build();

        bomba = repository.save(bomba);
        return mapper.toDTO(bomba);
    }

    public BombaCombustivelDTO alterar(Long id,BombaCombustivelRequestDTO request){
        BombaCombustivelModel bomba = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba de combustivel não encontrada"));

        if(request.nome() != null && !request.nome().isEmpty()){
            bomba.setNome(request.nome());
        }

        if(request.tipoCombustivelId() != null){
            TipoCombustivelModel tipoCombustivel = tipoRepository.findById(request.tipoCombustivelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo de combustivel não encontrado"));
            bomba.setTipoCombustivel(tipoCombustivel);
        }

        repository.save(bomba);
        return mapper.toDTO(bomba);
    }

    public void delete(Long id){
        BombaCombustivelModel bomba = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba de combustivel não encontrada"));
        repository.delete(bomba);
    }
}
