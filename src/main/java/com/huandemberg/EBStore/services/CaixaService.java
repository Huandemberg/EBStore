package com.huandemberg.EBStore.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huandemberg.EBStore.models.Caixa;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.repositories.CaixaRepository;
import com.huandemberg.EBStore.security.UserSpringSecurity;
import com.huandemberg.EBStore.services.exceptions.AuthorizationException;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    public Caixa create(Caixa obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) ||  !userSpringSecurity.hasRole(ProfileEnum.ADMIN)){
            throw new AuthorizationException("Acesso negado!");
            }
        obj.setId(null);
        obj = this.caixaRepository.save(obj);
        return obj;
       
    }

}
