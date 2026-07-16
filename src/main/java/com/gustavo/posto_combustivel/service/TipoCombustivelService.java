package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.mapper.TipoCombustivelMapper;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import com.gustavo.posto_combustivel.repository.TipoCombustivelRepository;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TipoCombustivelService {

    private final TipoCombustivelRepository repository;
    private final TipoCombustivelMapper mapper;

    public TipoCombustivelService(TipoCombustivelRepository repository, TipoCombustivelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TipoCombustivelDTO> listar(){
        List<TipoCombustivelModel> tipoComb = repository.findAll();
        return tipoComb.stream()
                .map(mapper :: toDTO)
                .toList();
    }

    public TipoCombustivelDTO listarPorId(Long id){
        TipoCombustivelModel tipoId = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de combustível não encontrado com o ID: " + id));

        return mapper.toDTO(tipoId);
    }

    public TipoCombustivelDTO salvar(TipoCombustivelDTO request){
        if(request.getNome() == null || request.getNome().isEmpty()){
            throw new BusinessException("Informe o nome do combustível.");
        }
        if(request.getPrecoLitro() == null || request.getPrecoLitro().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("Informe um preço válido para o combustível.");
        }
        TipoCombustivelModel novoTipo = mapper.toModel(request);
        novoTipo = repository.save(novoTipo);
        return mapper.toDTO(novoTipo);
    }

    public TipoCombustivelDTO alterarTipo(Long id, TipoCombustivelDTO request){
        TipoCombustivelModel tipoAtt = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de combustível não encontrado com o ID: " + id));

        tipoAtt.setNome(request.getNome() != null ? request.getNome() : tipoAtt.getNome());
        tipoAtt.setPrecoLitro(request.getPrecoLitro() != null ? request.getPrecoLitro() : tipoAtt.getPrecoLitro());

        return mapper.toDTO(repository.save(tipoAtt));
    }

    public void delete(Long id){
         listarPorId(id);
        repository.deleteById(id);
        }

}
