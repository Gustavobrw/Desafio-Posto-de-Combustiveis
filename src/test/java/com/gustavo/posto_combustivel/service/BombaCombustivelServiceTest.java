package com.gustavo.posto_combustivel.service;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.dto.BombaCombustivelRequestDTO;
import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.entity.BombaCombustivelModel;
import com.gustavo.posto_combustivel.entity.TipoCombustivelModel;
import com.gustavo.posto_combustivel.exception.BusinessException;
import com.gustavo.posto_combustivel.exception.ResourceNotFoundException;
import com.gustavo.posto_combustivel.mapper.BombaCombustivelMapper;
import com.gustavo.posto_combustivel.repository.BombaCombustivelRepository;
import com.gustavo.posto_combustivel.repository.TipoCombustivelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BombaCombustivelServiceTest {

    @Mock
    BombaCombustivelRepository bombaCombustivelRepository;
    @Mock
    BombaCombustivelMapper bombaCombustivelMapper;
    @Mock
    TipoCombustivelRepository tipoCombustivelRepository;

    @InjectMocks
    BombaCombustivelService bombaCombustivelService;

    @Test
    void deveRetornarBombaQuandoExistir() {
        Long id = 1L;
        BombaCombustivelModel bomba = criaBomba();
        BombaCombustivelDTO bombaDTO = criaBombaDTO();
        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.of(bomba));
        when(bombaCombustivelMapper.toDTO(bomba)).thenReturn(bombaDTO);

        BombaCombustivelDTO response = bombaCombustivelService.listarPorId(id);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(bombaDTO);
    }

    @Test
    void deveRetornarExceptionQuandoBombaNaoExistir() {
        Long id = 1L;

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bombaCombustivelService.listarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Bomba de combustivel não encontrada");

        verifyNoInteractions(bombaCombustivelMapper);
    }

    @Test
    void deveRetornarSucessoQuandoCriarBomba() {
        BombaCombustivelRequestDTO bombaRequest = criaBombaRequest();
        TipoCombustivelModel tipoCombustivel = criaTipoCombustivel();
        BombaCombustivelModel bombaModel = criaBomba();
        BombaCombustivelDTO bombaDTO = criaBombaDTO();

        when(tipoCombustivelRepository.findById(bombaRequest.tipoCombustivelId()))
                .thenReturn(Optional.of(tipoCombustivel));
        when(bombaCombustivelRepository.save(any(BombaCombustivelModel.class)))
                .thenReturn(bombaModel);
        when(bombaCombustivelMapper.toDTO(bombaModel)).thenReturn(bombaDTO);

        BombaCombustivelDTO response = bombaCombustivelService.salvar(bombaRequest);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(bombaDTO);

        ArgumentCaptor<BombaCombustivelModel> captor =
                ArgumentCaptor.forClass(BombaCombustivelModel.class);

        verify(bombaCombustivelRepository).save(captor.capture());
        BombaCombustivelModel bombaCaptor = captor.getValue();

        assertThat(bombaCaptor.getNome()).isEqualTo(bombaRequest.nome());
        assertThat(bombaCaptor.getTipoCombustivel().getId()).isEqualTo(bombaRequest.tipoCombustivelId());

        verify(bombaCombustivelMapper).toDTO(bombaModel);
    }

    @Test
    void deveRetornarExceptionQuandoNomeInvalido() {
        BombaCombustivelRequestDTO request = new BombaCombustivelRequestDTO(
                "",
                1L
        );

        assertThatThrownBy(() -> bombaCombustivelService.salvar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Nome da bomba de combustivel não pode ser nulo ou vazio");

        verifyNoInteractions(tipoCombustivelRepository);
        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(bombaCombustivelMapper);
    }

    @Test
    void deveRetornarExceptionQuandoTipoIdInvalido() {
        BombaCombustivelRequestDTO request = new BombaCombustivelRequestDTO(
                "Teste",
                null
        );

        assertThatThrownBy(() -> bombaCombustivelService.salvar(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Tipo de combustivel não pode ser nulo");

        verifyNoInteractions(tipoCombustivelRepository);
        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(bombaCombustivelMapper);
    }

    @Test
    void deveRetornarExceptionQuandoTipoCombustivelNaoExistir() {
        BombaCombustivelRequestDTO request = criaBombaRequest();

        when(tipoCombustivelRepository.findById(request.tipoCombustivelId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bombaCombustivelService.salvar(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de combustivel não encontrado");

        verifyNoInteractions(bombaCombustivelRepository);
        verifyNoInteractions(bombaCombustivelMapper);

    }

    @Test
    void deveRetornarSucessoQuandoAlterarBomba() {
        Long id = 1L;
        BombaCombustivelRequestDTO request = criaBombaRequest();
        BombaCombustivelModel bomba = criaBomba();
        TipoCombustivelModel tipoCombustivel = criaTipoCombustivel();
        BombaCombustivelDTO bombaDTO = criaBombaDTO();

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.of(bomba));
        when(tipoCombustivelRepository.findById(request.tipoCombustivelId())).thenReturn(Optional.of(tipoCombustivel));
        when(bombaCombustivelRepository.save(bomba)).thenReturn(bomba);
        when(bombaCombustivelMapper.toDTO(bomba)).thenReturn(bombaDTO);

        BombaCombustivelDTO response = bombaCombustivelService.alterar(id, request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(bombaDTO);

        verify(bombaCombustivelRepository).save(bomba);
        verify(bombaCombustivelMapper).toDTO(bomba);
    }

    @Test
    void deveRetornarExceptionQuandoBombaNaoExiste() {
        Long id = 1L;
        BombaCombustivelRequestDTO requestDTO = criaBombaRequest();

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bombaCombustivelService.alterar(id, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Bomba de combustivel não encontrada");

        verifyNoInteractions(tipoCombustivelRepository);
        verifyNoInteractions(bombaCombustivelMapper);
    }

    @Test
    void deveRetornarExceptionQuandoTipoCombustivelNaoExiste() {
        Long id = 1L;
        BombaCombustivelModel bomba = criaBomba();
        BombaCombustivelRequestDTO requestDTO = criaBombaRequest();

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.of(bomba));
        when(tipoCombustivelRepository.findById(requestDTO.tipoCombustivelId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bombaCombustivelService.alterar(id, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de combustivel não encontrado");

        verifyNoInteractions(bombaCombustivelMapper);
    }

    @Test
    void deveRetornarSucessoQuandoDeletarRegistroValido() {
        Long id = 1L;
        BombaCombustivelModel bomba = criaBomba();

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.of(bomba));

        bombaCombustivelService.delete(id);

        verify(bombaCombustivelRepository).findById(id);
        verify(bombaCombustivelRepository).delete(bomba);

    }

    @Test
    void deveRetornarExceptionQuandoDeletarBombaQueNaoExiste() {
        Long id = 1L;

        when(bombaCombustivelRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bombaCombustivelService.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Bomba de combustivel não encontrada");

        verify(bombaCombustivelRepository, never()).delete(any(BombaCombustivelModel.class));
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

    private BombaCombustivelRequestDTO criaBombaRequest() {
        return new BombaCombustivelRequestDTO(
                "Bomba Teste",
                1L
        );
    }

    private TipoCombustivelModel criaTipoCombustivel() {
        return new TipoCombustivelModel(
                1L,
                "Gasolina",
                new BigDecimal("6.56"),
                List.of()
        );
    }
}