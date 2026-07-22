package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.AbastecimentoDTO;
import com.gustavo.posto_combustivel.dto.AbastecimentoRequestDTO;
import com.gustavo.posto_combustivel.service.AbastecimentoService;
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
        name = "Abastecimento",
        description = "Gerenciamento de abastecimentos"
)
@RestController
@RequestMapping("/abastecimento")
@RequiredArgsConstructor
public class AbastecimentoControllerImp implements AbastecimentoController {

    private final AbastecimentoService service;

    @GetMapping
    public ResponseEntity<List<AbastecimentoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbastecimentoDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AbastecimentoDTO> salvar(@RequestBody AbastecimentoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbastecimentoDTO> alterar(@PathVariable Long id, @RequestBody AbastecimentoRequestDTO request) {
        return ResponseEntity.ok(service.alterar(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body("Abastecimento Deletado");
    }
}
