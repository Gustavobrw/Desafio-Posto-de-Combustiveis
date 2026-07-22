package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.TipoCombustivelDTO;
import com.gustavo.posto_combustivel.service.TipoCombustivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Tipos de combustível",
        description = "Gerenciamento dos tipos de combustível"
)
@RestController
@RequestMapping("/combustivel")
@RequiredArgsConstructor
public class TipoCombustivelControllerImp implements TipoCombustivelController {

    private final TipoCombustivelService service;

    @GetMapping
    public ResponseEntity<List<TipoCombustivelDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCombustivelDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoCombustivelDTO> salvar(@RequestBody TipoCombustivelDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCombustivelDTO> alterarTipo(@PathVariable Long id, @RequestBody TipoCombustivelDTO request) {
        return ResponseEntity.ok(service.alterarTipo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tipo Deletado");
    }
}
