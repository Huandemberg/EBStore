package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.projection.ProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    @Query("SELECT p.id AS id, p.modelo AS modelo, p.tamanho AS tamanho, p.cor AS cor, p.preco AS preco, p.estoque AS estoque FROM Produto p")
    List<ProdutoProjection> findByAllProdutos();

    List<Produto> findAllByModeloLike(String patters);

    @Query("SELECT p FROM Produto p WHERE p.id IN :ids")
    List<Produto> findAllByIds(@Param("ids") List<Long> ids);

}
