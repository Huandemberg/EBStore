package com.huandemberg.EBStore.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.huandemberg.EBStore.models.Caixa;
import com.huandemberg.EBStore.services.CaixaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/caixa")
@Validated
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @GetMapping("/{id}")
    public ResponseEntity<Caixa> findById(@PathVariable Long id){

        Caixa obj = this.caixaService.findById(id);
        return ResponseEntity.ok(obj);

    }

    @GetMapping("/caixas")
    public ResponseEntity<List<Caixa>> findByAllCaixas() {
        
        List<Caixa> objs = this.caixaService.findAllCaixas();
        return ResponseEntity.ok().body(objs);

    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Caixa obj) {

        Caixa caixa = this.caixaService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(caixa.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.caixaService.delete(id);
        return ResponseEntity.noContent().build();
        
    }


    
}
