package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TipoCombustivelController {

    @Operation(summary = "Lista todos os tipos de combustível")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<List<TipoCombustivelDTO>> listar();

    @Operation(summary = "Lista combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de combustível encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de combustível não encontrado")
    })
    ResponseEntity<TipoCombustivelDTO> listarPorId(@PathVariable Long id);

    @Operation(summary = "Salva um novo combustível")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Combustível salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar combustivel")
    })
    ResponseEntity<TipoCombustivelDTO> salvar(@RequestBody TipoCombustivelDTO request);

    @Operation(summary = "Altera combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combustível alterado"),
            @ApiResponse(responseCode = "400", description = "Dados do tipo de combustível inválidos"),
            @ApiResponse(responseCode = "404", description = "Combustível não encontrado")
    })
    ResponseEntity<TipoCombustivelDTO> alterarTipo(@PathVariable Long id, @RequestBody TipoCombustivelDTO request);

    @Operation(summary = "Deleta combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combustível deletado"),
            @ApiResponse(responseCode = "404", description = "Combustível não encontrado")
    })
    ResponseEntity<String> delete(@PathVariable Long id);
}
