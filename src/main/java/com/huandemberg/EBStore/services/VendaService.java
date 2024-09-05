package com.huandemberg.EBStore.services;

import com.huandemberg.EBStore.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

}
