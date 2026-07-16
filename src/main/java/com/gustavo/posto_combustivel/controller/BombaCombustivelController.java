package com.gustavo.posto_combustivel.controller;

import com.gustavo.posto_combustivel.dto.BombaCombustivelDTO;
import com.gustavo.posto_combustivel.dto.BombaCombustivelRequestDTO;
import com.gustavo.posto_combustivel.service.BombaCombustivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bomba")
@RequiredArgsConstructor
public class BombaCombustivelController {
    private final BombaCombustivelService service;

    @GetMapping
    public ResponseEntity<List<BombaCombustivelDTO>> listar (){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BombaCombustivelDTO> listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<BombaCombustivelDTO> salvar(@RequestBody BombaCombustivelRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BombaCombustivelDTO> alterar(@PathVariable Long id, @RequestBody BombaCombustivelRequestDTO request){
        return ResponseEntity.ok(service.alterar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Bomba deletada com sucesso");
    }

}
