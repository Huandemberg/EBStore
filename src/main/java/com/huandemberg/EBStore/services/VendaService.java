package com.huandemberg.EBStore.services;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.User;
import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.dto.VendaCreateDTO;
import com.huandemberg.EBStore.models.dto.VendaUpdateDTO;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.models.projection.VendaProjection;
import com.huandemberg.EBStore.repositories.ProdutoRepository;
import com.huandemberg.EBStore.repositories.VendaRepository;
import com.huandemberg.EBStore.security.UserSpringSecurity;
import com.huandemberg.EBStore.services.exceptions.AuthorizationException;
import com.huandemberg.EBStore.services.exceptions.DataBindingViolationException;
import com.huandemberg.EBStore.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

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
    private ProdutoRepository produtoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    public Venda findById(Long id) {
        Venda venda = this.vendaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Venda.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasVenda(userSpringSecurity, venda))
            throw new AuthorizationException("Acesso negado!");

        return venda;
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
        Produto produto = obj.getProduto();
        if(produto.getEstoque() >= obj.getQuantidade()){
            produto.reduzirEstoque(obj.getQuantidade());
            this.produtoRepository.save(produto);
        } else {
            throw new DataBindingViolationException("Estoque insuficiente para a operação solicitada");
        }
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

    private Boolean userHasVenda(UserSpringSecurity userSpringSecurity, Venda venda) {
        return venda.getUser().getId().equals(userSpringSecurity.getId());
    }

    public Venda fromDTO(@Valid VendaCreateDTO obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        User user = this.userService.findById(userSpringSecurity.getId());
        Produto produto = this.produtoService.findById(obj.getProduto_Id());
        Cliente cliente = this.clienteService.findById(obj.getCliente_Id());
        Venda venda = new Venda();
        venda.setUser(user);
        venda.setCliente(cliente);
        venda.setProduto(produto);
        venda.setValorCliente(obj.getValorCliente());
        venda.setData(obj.getData());
        venda.setFormPag(obj.getFormPag());
        venda.setQuantidade(obj.getEstoque());
        return venda;
    }

    public Venda fromDTO(@Valid VendaUpdateDTO obj) {
        Produto produto = this.produtoService.findById(obj.getProduto_Id());
        Cliente cliente = this.clienteService.findById(obj.getCliente_Id());
        Venda venda = new Venda();
        venda.setId(obj.getId());
        venda.setCliente(cliente);
        venda.setProduto(produto);
        venda.setValorCliente(obj.getValorCliente());
        venda.setFormPag(obj.getFormPag());
        venda.setQuantidade(obj.getEstoque());
        return venda;
    }
 
}
