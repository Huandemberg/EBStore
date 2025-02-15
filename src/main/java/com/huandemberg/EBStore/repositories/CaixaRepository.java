package com.huandemberg.EBStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huandemberg.EBStore.models.Caixa;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {
    
}
