package com.huandemberg.EBStore.controllers;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.projection.ClienteProjection;
import com.huandemberg.EBStore.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente obj = this.clienteService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteProjection>> findAllByUser() {
        List<ClienteProjection> objs = this.clienteService.findAllByUser();
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<Cliente>> findByNome(@PathVariable String nome){
        List<Cliente> obj = this.clienteService.findAllByNameLike(nome);
        return ResponseEntity.ok().body(obj);
    }
    
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Cliente obj){
        Cliente cliente = this.clienteService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Cliente obj, @PathVariable Long id){
        obj.setId(id);
        this.clienteService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
