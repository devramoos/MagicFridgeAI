package dev.java10x.MagicFridgeAI.controller;

import dev.java10x.MagicFridgeAI.model.Fooditem;
import dev.java10x.MagicFridgeAI.service.FooditemService;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FooditemController {

    private FooditemService service;

    public FooditemController(FooditemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Fooditem> criar (@RequestBody Fooditem fooditem){
       Fooditem salvo = service.salvar(fooditem);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Fooditem>>listar(){
        List<Fooditem> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fooditem> atualizar(@RequestBody Fooditem fooditem, @PathVariable Long id){
        return service.buscarPorId(id)
                .map(itemExistente ->{
                    fooditem.setId(itemExistente.getId());
                    Fooditem atualizado = service.atualizar(fooditem);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
