package com.gustavo.posto_combustivel.TipoCombustivel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/combustivel")
@RequiredArgsConstructor
public class TipoCombustivelController {

    private final TipoCombustivelService service;

    @GetMapping("/listar")
    public ResponseEntity<List<TipoCombustivelDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
      Optional<TipoCombustivelDTO> tipoId = service.listarPorId(id);
      if(tipoId.isPresent()) {
          return ResponseEntity.ok(tipoId);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo não encontrado");
    }

    @PostMapping("/salvar")
    public ResponseEntity<TipoCombustivelDTO> salvar(@RequestBody TipoCombustivelDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<TipoCombustivelDTO> alterarTipo (@PathVariable Long id, @RequestBody TipoCombustivelDTO request){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.alterarTipo(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
       if (service.listarPorId(id).isPresent()){
            service.delete(id);
            return ResponseEntity.ok("Tipo Deletado");
       }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo não encontrado");
    }
}
