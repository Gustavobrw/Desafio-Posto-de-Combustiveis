package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.dto.BombaCombustivelRequestDTO;
import com.gustavo.posto_combustivel.service.BombaCombustivelService;
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
        name = "Bomba de Combustível",
        description = "Gerenciamento das bombas de combustível"
)
@RestController
@RequestMapping("/bomba")
@RequiredArgsConstructor
public class BombaCombustivelControllerImp implements BombaCombustivelController {
    private final BombaCombustivelService service;

    @GetMapping
    public ResponseEntity<List<BombaCombustivelDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BombaCombustivelDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<BombaCombustivelDTO> salvar(@RequestBody BombaCombustivelRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BombaCombustivelDTO> alterar(@PathVariable Long id, @RequestBody BombaCombustivelRequestDTO request) {
        return ResponseEntity.ok(service.alterar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body("Bomba deletada com sucesso");
    }

}
