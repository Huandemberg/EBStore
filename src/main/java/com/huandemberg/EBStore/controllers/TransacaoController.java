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

import com.huandemberg.EBStore.models.Transacao;
import com.huandemberg.EBStore.services.TransacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacao")
@Validated
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id) {

        Transacao obj = this.transacaoService.findById(id);
        return ResponseEntity.ok(obj);

    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<Transacao>> findAllTransacoes() {

        List<Transacao> objs = this.transacaoService.findAll();
        return ResponseEntity.ok().body(objs);
    }
    
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Transacao obj) {

        Transacao transacao = this.transacaoService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(transacao.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        this.transacaoService.delete(id);
        return ResponseEntity.noContent().build();

    }

}
