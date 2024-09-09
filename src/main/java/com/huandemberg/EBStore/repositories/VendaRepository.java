package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<VendaProjection> findAllVendas();

}
