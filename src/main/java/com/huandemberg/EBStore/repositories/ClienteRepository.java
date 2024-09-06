package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.projection.ClienteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<ClienteProjection> findByAllClientes();

}
