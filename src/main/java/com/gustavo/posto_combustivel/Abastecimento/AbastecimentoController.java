package com.gustavo.posto_combustivel.Abastecimento;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimento")
@RequiredArgsConstructor
public class AbastecimentoController {

    private final AbastecimentoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<AbastecimentoDTO>> listar (){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        AbastecimentoDTO abastecimentoId = service.listarPorId(id);
        if(abastecimentoId != null){
            return ResponseEntity.ok(abastecimentoId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento não encontrado");
    }

    @PostMapping("/salvar")
    public ResponseEntity<AbastecimentoDTO> salvar(@RequestBody AbastecimentoDTO abastecimentoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(abastecimentoDTO));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody AbastecimentoDTO abastecimentoDTO){
        if(service.listarPorId(id) != null){
            return ResponseEntity.ok(service.alterar(id, abastecimentoDTO));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento não encontrado");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(service.listarPorId(id) != null){
            service.delete(id);
            return ResponseEntity.ok("Abastecimento deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento não encontrado");
    }
}
