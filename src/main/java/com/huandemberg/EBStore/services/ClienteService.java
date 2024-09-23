package com.huandemberg.EBStore.services;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.User;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.models.projection.ClienteProjection;
import com.huandemberg.EBStore.repositories.ClienteRepository;
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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserService userService;

    public Cliente findById(Long id){
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasCliente(userSpringSecurity, cliente))
            throw new AuthorizationException("Acesso negado!");
        return cliente;
    }

    public List<ClienteProjection> findAllByUser(){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        List<ClienteProjection> clients = this.clienteRepository.findAllClienteProjections();
        return clients;
    }

    @Transactional
    public Cliente create(Cliente obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        User user = this.userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.clienteRepository.save(obj);
        return obj;
    }

    @Transactional
    public Cliente update(Cliente obj) {
        Cliente newObj = findById(obj.getId());
        newObj.setEmail(obj.getEmail());
        newObj.setNome(obj.getNome());
        newObj.setDataNasc(obj.getDataNasc());
        return this.clienteRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    private Boolean userHasCliente(UserSpringSecurity userSpringSecurity, Cliente cliente){
        return cliente.getUser().getId().equals(userSpringSecurity.getId());
    }

}
