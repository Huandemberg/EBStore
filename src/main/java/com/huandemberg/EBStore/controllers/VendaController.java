package com.huandemberg.EBStore.controllers;

import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.dto.VendaCreateDTO;
import com.huandemberg.EBStore.models.dto.VendaUpdateDTO;
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
    public ResponseEntity<Void> create(@Valid @RequestBody VendaCreateDTO obj){
        Venda venda = this.vendaService.fromDTO(obj);
        Venda newVenda = this.vendaService.create(venda);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newVenda.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody VendaUpdateDTO obj, @PathVariable Long id ) {
        obj.setId(id);
        Venda venda = this.vendaService.fromDTO(obj);
        this.vendaService.update(venda);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.vendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
