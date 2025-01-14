package com.huandemberg.EBStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huandemberg.EBStore.models.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    
    
}
