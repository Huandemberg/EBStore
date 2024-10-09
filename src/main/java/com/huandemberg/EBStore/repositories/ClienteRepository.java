package com.huandemberg.EBStore.repositories;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.projection.ClienteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c.id AS id, c.nome AS nome, c.cpf AS cpf, c.dataNasc AS dataNasc, c.email AS email FROM Cliente c")
    List<ClienteProjection> findAllClienteProjections();

    List<Cliente> findAllByNome(String nome);

    List<Cliente> findAllByNomeLike(String patters);

    List<Cliente> findTop10ByNomeLike(String patters);

}
