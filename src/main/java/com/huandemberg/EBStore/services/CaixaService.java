package com.huandemberg.EBStore.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huandemberg.EBStore.models.Caixa;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.repositories.CaixaRepository;
import com.huandemberg.EBStore.security.UserSpringSecurity;
import com.huandemberg.EBStore.services.exceptions.AuthorizationException;
import com.huandemberg.EBStore.services.exceptions.DataBindingViolationException;
import com.huandemberg.EBStore.services.exceptions.ObjectNotFoundException;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    public Caixa findById(Long id) {

        Caixa caixa = this.caixaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Caixa.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)) {
            throw new AuthorizationException("Acesso negado!");
        }

        return caixa;

    }

    public List<Caixa> findAllCaixas() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)){
            throw new AuthorizationException("Acesso negado!");
        }
        List<Caixa> caixas = this.caixaRepository.findAll();
        return caixas;
        
    }

    public Caixa create(Caixa obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)) {
            throw new AuthorizationException("Acesso negado!");
        }
        obj.setId(null);
        obj = this.caixaRepository.save(obj);
        return obj;

    }

    public Caixa updateIncrementar(Caixa obj, float valor) {
        Caixa newObj = findById(obj.getId());
        newObj.addValor(valor);
        return this.caixaRepository.save(newObj);
    }

    public Caixa updateDecrementar(Caixa obj, float valor) {
        Caixa newObj = findById(obj.getId());
        newObj.reduzirValor(valor);
        return this.caixaRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.caixaRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há valores em aberto!");
        }
    }

}
