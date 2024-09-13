package com.huandemberg.EBStore.controllers;

import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import com.huandemberg.EBStore.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/venda")
@Validated
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> findById(@PathVariable Long id) {
        Venda obj = this.vendaService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<VendaProjection>> findAllByVendas() {
        List<VendaProjection> objs = this.vendaService.findAllByVendas();
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Venda obj){
        this.vendaService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Venda obj, @PathVariable Long id ) {
        obj.setId(id);
        this.vendaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.vendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
