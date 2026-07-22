package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.AbastecimentoDTO;
import com.gustavo.posto_combustivel.dto.AbastecimentoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AbastecimentoController {

    @Operation(summary = "Lista todos os abastecimentos")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<List<AbastecimentoDTO>> listar();

    @Operation(summary = "Lista abastecimento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abastecimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Abastecimento não encontrado")
    })
    ResponseEntity<AbastecimentoDTO> listarPorId(@PathVariable Long id);

    @Operation(summary = "Salva um novo abastecimento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Abastecimento salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do abastecimento inválidos"),
            @ApiResponse(responseCode = "404", description = "Tipo de combustível não encontrado")
    })
    ResponseEntity<AbastecimentoDTO> salvar(@RequestBody AbastecimentoRequestDTO request);

    @Operation(summary = "Altera abastecimento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abastecimento alterado"),
            @ApiResponse(responseCode = "400", description = "Dados do abastecimento inválidos"),
            @ApiResponse(responseCode = "404", description = "Abastecimento não encontrado")
    })
    ResponseEntity<AbastecimentoDTO> alterar(@PathVariable Long id, @RequestBody AbastecimentoRequestDTO request);

    @Operation(summary = "Deleta abastecimento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abastecimento deletado"),
            @ApiResponse(responseCode = "404", description = "Abastecimento não encontrado")
    })
    ResponseEntity<String> delete(@PathVariable Long id);
}
