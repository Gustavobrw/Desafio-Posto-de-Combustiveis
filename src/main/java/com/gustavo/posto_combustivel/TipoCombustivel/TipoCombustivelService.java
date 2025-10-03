package com.gustavo.posto_combustivel.TipoCombustivel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<TipoCombustivelDTO> listarPorId(Long id){
        Optional<TipoCombustivelModel> tipoPorId = repository.findById(id);
        if(tipoPorId.isPresent()){
            return tipoPorId.map(mapper::toDTO);
        }
        return Optional.empty();
    }

    public TipoCombustivelDTO salvar(TipoCombustivelDTO request){
        TipoCombustivelModel novoTipo = mapper.toModel(request);
        novoTipo = repository.save(novoTipo);
        return mapper.toDTO(novoTipo);
    }

    public TipoCombustivelDTO alterarTipo(Long id, TipoCombustivelDTO request){
        Optional<TipoCombustivelDTO> tipoExiste = listarPorId(id);
        if(tipoExiste.isPresent()){
            TipoCombustivelModel tipoAtt = mapper.toModel(request);
            tipoAtt.setId(id);
            TipoCombustivelModel tipoSalvo = repository.save(tipoAtt);
            return mapper.toDTO(tipoSalvo);
        }
        return null;
    }

    public void delete(Long id){
         repository.deleteById(id);
        }

}
