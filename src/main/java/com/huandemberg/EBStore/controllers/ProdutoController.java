package com.huandemberg.EBStore.controllers;

import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.projection.ProdutoProjection;
import com.huandemberg.EBStore.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        Produto obj = this.produtoService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoProjection>> findAllByProdutos(){
        List<ProdutoProjection> objs = this.produtoService.findAllByProdutos();
        return ResponseEntity.ok().body(objs);
    }

    @GetMapping("/findByModel/{nome}")
    public ResponseEntity<List<Produto>> findAllByModelo(@PathVariable String nome){
        List<Produto> objs = this.produtoService.findAllByModeloLike(nome);
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Produto obj){
        this.produtoService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Produto obj, @PathVariable Long id) {
        obj.setId(id);
        this.produtoService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
