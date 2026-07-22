package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.dto.BombaCombustivelRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BombaCombustivelController {

    @Operation(summary = "Lista todas as bombas de combustível")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<BombaCombustivelDTO>> listar();

    @Operation(summary = "Lista bomba de combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bomba de combustível encontrada"),
            @ApiResponse(responseCode = "404", description = "Bomba de combustível não encontrada")
    })
    ResponseEntity<BombaCombustivelDTO> listarPorId(@PathVariable Long id);

    @Operation(summary = "Salva uma nova bomba de combustível")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bomba de combustível salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da bomba de combustível inválidos"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar bomba de combustivel")
    })
    ResponseEntity<BombaCombustivelDTO> salvar(@RequestBody BombaCombustivelRequestDTO request);

    @Operation(summary = "Altera bomba de combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bomba de combustível alterada"),
            @ApiResponse(responseCode = "400", description = "Dados da bomba de combustível inválidos"),
            @ApiResponse(responseCode = "404", description = "Bomba de combustível não encontrada")
    })
    ResponseEntity<BombaCombustivelDTO> alterar(@PathVariable Long id, @RequestBody BombaCombustivelRequestDTO request);

    @Operation(summary = "Deleta bomba de combustível por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bomba de combustível deletada"),
            @ApiResponse(responseCode = "404", description = "Bomba de combustível não encontrada")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);
}
