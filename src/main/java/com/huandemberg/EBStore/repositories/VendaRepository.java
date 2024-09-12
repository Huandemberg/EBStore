package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT v.id AS id, v.produto AS produto, v.valorCliente AS valorCliente," +
            " v.formPag AS formPag, v.data AS data, v.user AS user, v.cliente AS cliente FROM Venda v")
    List<VendaProjection> findAllVendas();

}
