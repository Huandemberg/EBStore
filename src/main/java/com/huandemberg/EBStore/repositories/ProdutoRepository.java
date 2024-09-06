package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.projection.ProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<ProdutoProjection> findByAllProdutos();

}
