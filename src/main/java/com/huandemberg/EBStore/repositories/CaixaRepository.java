package com.huandemberg.EBStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huandemberg.EBStore.models.Caixa;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
    
}
