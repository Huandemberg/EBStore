package com.huandemberg.EBStore.services;

import com.huandemberg.EBStore.models.User;
import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import com.huandemberg.EBStore.repositories.VendaRepository;
import com.huandemberg.EBStore.security.UserSpringSecurity;
import com.huandemberg.EBStore.services.exceptions.AuthorizationException;
import com.huandemberg.EBStore.services.exceptions.DataBindingViolationException;
import com.huandemberg.EBStore.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private UserService userService;

    public Venda findById(Long id) {
        Venda task = this.vendaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Venda.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task))
            throw new AuthorizationException("Acesso negado!");

        return task;
    }

    public List<VendaProjection> findAllByVendas() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<VendaProjection> vendas = this.vendaRepository.findAllVendas();
        return vendas;
    }

    @Transactional
    public Venda create(Venda obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        User user = this.userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.vendaRepository.save(obj);
        return obj;
    }

    @Transactional
    public Venda update(Venda obj) {
        Venda newObj = findById(obj.getId());
        newObj.setCliente(obj.getCliente());
        newObj.setProduto(obj.getProduto());
        newObj.setValorCliente(obj.getValorCliente());
        newObj.setFormPag(obj.getFormPag());
        return this.vendaRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.vendaRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Venda venda) {
        return venda.getUser().getId().equals(userSpringSecurity.getId());
    }

}
