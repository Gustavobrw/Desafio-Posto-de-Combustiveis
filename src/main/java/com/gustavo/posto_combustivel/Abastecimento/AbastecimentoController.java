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

    @GetMapping
    public ResponseEntity<List<AbastecimentoDTO>> listar (){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbastecimentoDTO> listarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AbastecimentoDTO> salvar(@RequestBody AbastecimentoRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody AbastecimentoRequestDTO request){
        return ResponseEntity.ok(service.alterar(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
       service.delete(id);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body("Abastecimento Deletado");
    }
}
