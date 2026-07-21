package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.*;
import com.gustavo.posto_combustivel.entity.AbastecimentoModel;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import com.gustavo.posto_combustivel.mapper.AbastecimentoMapper;
import com.gustavo.posto_combustivel.repository.AbastecimentoRepository;
import com.gustavo.posto_combustivel.repository.BombaCombustivelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbastecimentoServiceTest {
    @Mock
    AbastecimentoRepository abastecimentoRepository;

    @Mock
    AbastecimentoMapper abastecimentoMapper;

    @Mock
    BombaCombustivelRepository bombaCombustivelRepository;

    @InjectMocks
    AbastecimentoService abastecimentoService;

    @Test
    void deveRetornarSucessoQuandoBuscarAbastecimentoPorId(){
        Long id = 1L;
        AbastecimentoModel abastecimento = criaAbastecimento();
        AbastecimentoDTO abastecimentoDTO = criaAbastecimentoDTO();

        when(abastecimentoRepository.findById(id)).thenReturn(Optional.of(abastecimento));
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);
        AbastecimentoDTO response = abastecimentoService.listarPorId(id);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(abastecimentoDTO);
    }

    @Test
    void deveRetornarExceptionQuandoAbastecimentoNaoEncontrado(){
        Long id = 1L;

        when(abastecimentoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> abastecimentoService.listarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Abastecimento com o id "+ id + " não encontrado");

        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarSucessoQuandoCriarAbastecimentoValido(){
        AbastecimentoRequestDTO request = criaAbastecimentoRequest();
        BombaCombustivelModel bomba = criaBomba();
        AbastecimentoModel abastecimento = criaAbastecimento();
        AbastecimentoDTO abastecimentoDTO = criaAbastecimentoDTO();

        when(bombaCombustivelRepository.findById(request.bombaId())).thenReturn(Optional.of(bomba));
        when(abastecimentoRepository.save(any(AbastecimentoModel.class))).thenReturn(abastecimento);
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);

        AbastecimentoDTO response = abastecimentoService.salvar(request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(abastecimentoDTO);

        ArgumentCaptor<AbastecimentoModel> captor =
                ArgumentCaptor.forClass(AbastecimentoModel.class);

        verify(abastecimentoRepository).save(captor.capture());
        AbastecimentoModel abastecimentoCaptor = captor.getValue();

      assertThat(abastecimentoCaptor.getLitros()).isEqualByComparingTo(request.litros());
      assertThat(abastecimentoCaptor.getData()).isEqualTo(request.data());
      assertThat(abastecimentoCaptor.getBomba()).isSameAs(bomba);

      BigDecimal valorTotalEsperado = bomba
              .getTipoCombustivel()
              .getPrecoLitro()
              .multiply(request.litros());

      assertThat(abastecimentoCaptor.getValorTotal()).isEqualByComparingTo(valorTotalEsperado);
    }

    @Test
    void deveRetornarExceptionQuandoBombaNaoInformada(){
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(), new BigDecimal("10.0"),null
        );

        assertThatThrownBy(() -> abastecimentoService.salvar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("A bomba de combustível deve ser informada para realizar o abastecimento.");

        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(abastecimentoRepository);
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarExceptionQuandoLitrosInvalido(){
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(), null,1L
        );

        assertThatThrownBy(() -> abastecimentoService.salvar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("A quantidade de litros deve ser maior que zero.");

        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(abastecimentoRepository);
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarExceptionQuandoLitrosNegativos(){
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(), new BigDecimal("-1.0"),1L
        );

        assertThatThrownBy(() -> abastecimentoService.salvar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("A quantidade de litros deve ser maior que zero.");

        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(abastecimentoRepository);
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarExceptionQuandoBombaNaoEncontrada(){
        BombaCombustivelModel bomba = criaBomba();
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(), new BigDecimal("10.0"),1L
        );

        when(bombaCombustivelRepository.findById(request.bombaId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> abastecimentoService.salvar(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Bomba de combustível não encontrada");

        verifyNoInteractions(abastecimentoRepository);
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarSucessoAoAlterarAbastecimento(){
        Long id = 1L;
        AbastecimentoModel abastecimento = criaAbastecimento();
        AbastecimentoDTO abastecimentoDTO = criaAbastecimentoDTO();
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(),
                null,
                null
        );

        when(abastecimentoRepository.findById(id)).thenReturn(Optional.of(abastecimento));
        when(abastecimentoRepository.save(abastecimento)).thenReturn(abastecimento);
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);

        AbastecimentoDTO response = abastecimentoService.alterar(id,request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(abastecimentoDTO);

        verify(abastecimentoRepository).findById(id);
        verify(abastecimentoRepository).save(abastecimento);
        verify(abastecimentoMapper).toDTO(abastecimento);
    }

    @Test
    void deveRetornarExceptionQuandoAlterarAbastecimentoNulo(){
        Long id = 1L;
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(),
                new BigDecimal("10.00"),
                1L
        );
        when(abastecimentoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> abastecimentoService.alterar(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Abastecimento com o id "+ id + " não encontrado");

        verify(abastecimentoRepository,never()).save(any(AbastecimentoModel.class));
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarExceptionQuandoEnviarLitros(){
        Long id = 1L;
        AbastecimentoModel abastecimento = criaAbastecimento();
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(),
                new BigDecimal("10.00"),
                1L
        );
        when(abastecimentoRepository.findById(id)).thenReturn(Optional.of(abastecimento));

        assertThatThrownBy(() -> abastecimentoService.alterar(id, request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Não é permitido alterar a quantidade de litros do abastecimento.");

        verify(abastecimentoRepository,never()).save(any(AbastecimentoModel.class));
        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarExceptionQuandoEnviarBombaId(){
        Long id = 1L;
        AbastecimentoModel abastecimento = criaAbastecimento();
        AbastecimentoRequestDTO request = new AbastecimentoRequestDTO(
                LocalDateTime.now(),
                null,
                1L
        );
        when(abastecimentoRepository.findById(id)).thenReturn(Optional.of(abastecimento));

        assertThatThrownBy(() -> abastecimentoService.alterar(id, request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Não é permitido alterar a bomba de combustível do abastecimento.");

        verifyNoInteractions(abastecimentoMapper);
    }

    @Test
    void deveRetornarSucessoQuandoDeletarAbastecimentoValido(){
        Long id = 1L;
        AbastecimentoModel abastecimento = criaAbastecimento();

        when(abastecimentoRepository.findById(id)).thenReturn(Optional.of(abastecimento));

        abastecimentoService.delete(id);

        verify(abastecimentoRepository).findById(id);
        verify(abastecimentoRepository).delete(abastecimento);
    }

    @Test
    void deveRetornarExceptionQuandoAbastecimentoNaoExiste(){
        Long id = 1L;

        when(abastecimentoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> abastecimentoService.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "Abastecimento não encontrado");

        verify(abastecimentoRepository, never())
                .delete(any(AbastecimentoModel.class));
    }

    private AbastecimentoDTO criaAbastecimentoDTO(){
        return new AbastecimentoDTO(
                1L,
                LocalDateTime.now(),
                new BigDecimal("30.0"), //total
                new BigDecimal("10.0"), //litros
                criaBombaDTO()
        );
    }
    private AbastecimentoRequestDTO criaAbastecimentoRequest(){
        return new AbastecimentoRequestDTO(
                LocalDateTime.now(),
                new BigDecimal("10.0"), //litros
                1L
        );
    }
    private AbastecimentoModel criaAbastecimento(){
        return new AbastecimentoModel(
                1L,
                LocalDateTime.now(),
                new BigDecimal("30.0"), //total
                new BigDecimal("10.0"), //litros
                criaBomba()
        );
    }

    private BombaCombustivelModel criaBomba() {
        return new BombaCombustivelModel(
                null,
                "Bomba Teste",
                new TipoCombustivelModel(
                        1L,
                        "TipoTeste",
                        new BigDecimal("3.5"),
                        null),
                null
        );
    }

    private BombaCombustivelDTO criaBombaDTO() {

        return new BombaCombustivelDTO(
                1L,
                "Bomba Teste",
                new TipoCombustivelDTO(
                        1L,
                        "Gasolina",
                        new BigDecimal("6.56")
                )
        );
    }

}