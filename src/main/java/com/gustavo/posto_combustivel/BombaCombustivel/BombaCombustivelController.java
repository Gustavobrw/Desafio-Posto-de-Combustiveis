package com.gustavo.posto_combustivel.BombaCombustivel;

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

    @GetMapping("/listar")
    public ResponseEntity<List<BombaCombustivelDTO>> listar (){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        BombaCombustivelDTO bombaId = service.listarPorId(id);
        if( bombaId != null){
            return ResponseEntity.ok(bombaId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bomba de combustivel não encontrada");
    }

    @PostMapping("/salvar")
    public ResponseEntity<BombaCombustivelDTO> salvar(@RequestBody BombaCombustivelDTO bombaCombustivelDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(bombaCombustivelDTO));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody BombaCombustivelDTO bombaCombustivelDTO){
            if (service.listarPorId(id) != null) {
                return ResponseEntity.ok(service.alterar(id, bombaCombustivelDTO));
            }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bomba de combustivel não encontrada");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(service.listarPorId(id) != null){
            service.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Bomba deletada com sucesso");
        }
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bomba de combustivel não encontrada");
    }

}
