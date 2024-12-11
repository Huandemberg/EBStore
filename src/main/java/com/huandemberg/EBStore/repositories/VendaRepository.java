package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT v.id AS id, v.quantidade AS quantidade , v.produto AS produto, v.valorCliente AS valorCliente," +
            " v.formPag AS formPag, v.data AS data, v.user AS user, v.cliente AS cliente FROM Venda v")
    List<VendaProjection> findAllVendas();

    @Query("SELECT SUM(v.valorCliente) FROM Venda v WHERE v.situacao = 0")
    Double findValorDebito();

    @Query("SELECT SUM(v.valorCliente) FROM Venda v WHERE v.cliente.id = :clienteId AND v.situacao = 0")
    Double findValorDebitoCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT DISTINCT v.cliente FROM Venda v WHERE v.situacao = 0")
    List<Cliente> findClientesEmDebito();

    @Query("SELECT v " +
           "FROM Venda v " +
           "WHERE v.data BETWEEN :startDate AND :endDate " +
           "AND v.situacao = 0")
    List<Venda> findVendasByDataAndSituacao(@Param("startDate") String startDate, 
                                            @Param("endDate") String endDate);
    
}
