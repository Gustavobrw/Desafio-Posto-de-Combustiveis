package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import com.gustavo.posto_combustivel.mapper.TipoCombustivelMapper;
import com.gustavo.posto_combustivel.repository.TipoCombustivelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoCombustivelServiceTest {

    @Mock
    TipoCombustivelRepository tipoCombustivelRepository;

    @Mock
    TipoCombustivelMapper tipoCombustivelMapper;
    @InjectMocks
    TipoCombustivelService tipoCombustivelService;

    @Test
    void deveRetornarTipoCombustivel() {
        Long id = 1L;

        TipoCombustivelModel tipoCombustivel= geraTipoCombustivelValido();

        TipoCombustivelDTO tipoCombustivelDTO = geraTipoCombustivelDTO();

        when(tipoCombustivelRepository.findById(id)).thenReturn(Optional.of(tipoCombustivel));
        when(tipoCombustivelMapper.toDTO(tipoCombustivel)).thenReturn(tipoCombustivelDTO);

       TipoCombustivelDTO response = tipoCombustivelService.listarPorId(id);

       assertThat(response).isNotNull();
       assertThat(response).isEqualTo(tipoCombustivelDTO);

       verify(tipoCombustivelRepository).findById(id);
       verify(tipoCombustivelMapper).toDTO(tipoCombustivel);
    }

    @Test
    void deveRetornarExceptionQuandoNaoExistir(){
        Long id = 1L;

        when(tipoCombustivelRepository.findById(id)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> tipoCombustivelService.listarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de combustível não encontrado com o ID: " + id);
    }

    @Test
    void deveRetonarSucessoQuandoSalvarTipo(){
        TipoCombustivelDTO novoTipo = geraTipoCombustivelDTO();
        TipoCombustivelModel novoTipoModel = geraTipoCombustivelValido();

        when(tipoCombustivelMapper.toModel(novoTipo)).thenReturn(novoTipoModel);
        when(tipoCombustivelRepository.save(novoTipoModel)).thenReturn(novoTipoModel);
        when(tipoCombustivelMapper.toDTO(novoTipoModel)).thenReturn(novoTipo);

        TipoCombustivelDTO response = tipoCombustivelService.salvar(novoTipo);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(novoTipo);

        verify(tipoCombustivelRepository).save(novoTipoModel);
        verify(tipoCombustivelMapper).toModel(novoTipo);
        verify(tipoCombustivelMapper).toDTO(novoTipoModel);

    }

    @Test
    void deveRetornarExceptionQuandoNomeInvalido(){
        TipoCombustivelDTO novoTipo = new TipoCombustivelDTO(1L,null,new BigDecimal("3.5"));

        assertThatThrownBy(() -> tipoCombustivelService.salvar(novoTipo))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Informe o nome do combustível.");

        verifyNoInteractions(tipoCombustivelRepository);
        verifyNoInteractions(tipoCombustivelMapper);
    }

    @Test
    void deveRetornarExceptionQuandoValorInvalido(){
        TipoCombustivelDTO novoTipo = new TipoCombustivelDTO(1L,"teste",null);

        assertThatThrownBy(() -> tipoCombustivelService.salvar(novoTipo))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Informe um preço válido para o combustível.");

        verifyNoInteractions(tipoCombustivelRepository);
        verifyNoInteractions(tipoCombustivelMapper);
    }

    @Test
    void deveRetornarSucessoQuandoAlterarTipo(){
        Long id = 1L;
        TipoCombustivelDTO tipoDTO = geraTipoCombustivelDTO();
        TipoCombustivelModel tipoModel = geraTipoCombustivelValido();

        when(tipoCombustivelRepository.findById(id)).thenReturn(Optional.of(tipoModel));
        when(tipoCombustivelRepository.save(tipoModel)).thenReturn(tipoModel);
        when(tipoCombustivelMapper.toDTO(tipoModel)).thenReturn(tipoDTO);

        TipoCombustivelDTO response = tipoCombustivelService.alterarTipo(id, tipoDTO);

        assertThat(response).isNotNull();


        verify(tipoCombustivelRepository).save(tipoModel);
    }

    @Test
    void deveRetornarExceptionQuandoTipoNaoExiste(){
        Long id = 1L;
        TipoCombustivelDTO tipo = geraTipoCombustivelDTO();

        when(tipoCombustivelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tipoCombustivelService.alterarTipo(id,tipo))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de combustível não encontrado com o ID: " + id);

    }

    private TipoCombustivelModel geraTipoCombustivelValido(){
        return new TipoCombustivelModel(
                1L,
                "Gasolina",
               new BigDecimal("6.56"),
                List.of()
        );
    }

    private TipoCombustivelDTO geraTipoCombustivelDTO(){
        return new TipoCombustivelDTO(
                null,
                "Gasolina",
                new BigDecimal("6.56")
        );
    }
}