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

    public Double findDebito() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        Double valor = this.vendaRepository.findValorDebito();
        return valor;
    }

    public Double findDebitoCliente(Long id) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        Double valor = this.vendaRepository.findValorDebitoCliente(id);
        return valor;
    }

    public List<Cliente> findClientesDebito() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        List<Cliente> clientes = this.vendaRepository.findClientesEmDebito();
        return clientes;
    }

    public List<Venda> findVendasByDate(String startDate, String endDate ) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
            List<Venda> vendas = this.vendaRepository.findVendasByDataAndSituacao(startDate, endDate);
        return vendas;
    }

    @Transactional
    public Venda create(Venda obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        User user = this.userService.findById(userSpringSecurity.getId());
        List<Produto> produtos = obj.getProduto();
        if(produtos.size() != obj.getQuantidade().size() - 1) {

            for(int i = 0; i < produtos.size(); i++){
            
                Produto produto = produtos.get(i);
                int quantidade = obj.getQuantidade().get(i);

                if (produto.getEstoque() >= quantidade) {
                    produto.reduzirEstoque(quantidade);
                    this.produtoRepository.save(produto);
                } else {
                    throw new DataBindingViolationException("Estoque insuficiente para a operação solicitada");
                }
            }

        } else {
            throw new DataBindingViolationException("Quantidade de produtos não informado corretamente");
        }
        
        /*for (Produto produto : produtos) {
            if (produto.getEstoque() >= obj.getQuantidade()) {
                produto.reduzirEstoque(obj.getQuantidade());
                this.produtoRepository.save(produto);
            } else {
                throw new DataBindingViolationException("Estoque insuficiente para a operação solicitada");
            }
        } */
        obj.setSituacao(0);
        obj.setId(null);
        obj.setUser(user);
        obj = this.vendaRepository.save(obj);
        return obj;
    }

    @Transactional
    public Venda update(Venda obj) {
        Venda newObj = findById(obj.getId());
        List<Produto> produtos = obj.getProduto();
        newObj.setCliente(obj.getCliente());
        newObj.setProduto(obj.getProduto());
        newObj.setValorCliente(obj.getValorCliente());
        newObj.setFormPag(obj.getFormPag());
        newObj.setSituacao(obj.getSituacao());
        if(produtos.size() != obj.getQuantidade().size()) {

            for(int i = 0; i < produtos.size(); i++){
            
                Produto produto = produtos.get(i);
                int quantidade = obj.getQuantidade().get(i);
                int newquantidade = newObj.getQuantidade().get(i);

                if (produto.getEstoque() >= (quantidade - newquantidade)) {
                    produto.reduzirEstoque(quantidade - newquantidade);
                    this.produtoRepository.save(produto);
                } else {
                    throw new DataBindingViolationException("Estoque insuficiente para a operação solicitada");
                }
            }

        } else {
            throw new DataBindingViolationException("Quantidade de produtos não informado corretamente");
        }
        /* 
        for (Produto produto : produtos) {
            if (produto.getEstoque() >= (obj.getQuantidade() - newObj.getQuantidade())) {
                produto.reduzirEstoque(obj.getQuantidade() - newObj.getQuantidade());
                this.produtoRepository.save(produto);
            } else {
                throw new DataBindingViolationException("Estoque insuficiente para a operação solicitada");
            }
        }*/
        newObj.setQuantidade(obj.getQuantidade());
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
        List<Long> prodIds = obj.getProduto_Id();
        List<Produto> produtos = this.produtoService.findAllByIds(prodIds);
        Cliente cliente = this.clienteService.findById(obj.getCliente_Id());
        Venda venda = new Venda();  
        for(int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            Integer quantidade = obj.getEstoque().get(i);
            venda.setValorCliente(venda.getValorCliente() + produto.getPreco() * quantidade);

        }
        venda.setUser(user);
        venda.setCliente(cliente);
        venda.setProduto(produtos);
        venda.setData(obj.getData());
        venda.setFormPag(obj.getFormPag());
        venda.setQuantidade(obj.getEstoque());
        return venda;
    }

    public Venda fromDTO(@Valid VendaUpdateDTO obj) {
        List<Long> prodIds = obj.getProduto_Id();
        List<Produto> produtos = this.produtoService.findAllByIds(prodIds);
        Cliente cliente = this.clienteService.findById(obj.getCliente_Id());
        Venda venda = new Venda();
        for (Produto produto : produtos) {
            venda.setValorCliente(venda.getValorCliente() + produto.getPreco());
            
        }
        venda.setId(obj.getId());
        venda.setCliente(cliente);
        venda.setProduto(produtos);
        venda.setFormPag(obj.getFormPag());
        venda.setQuantidade(obj.getEstoque());
        if(obj.getSituacao() == 0 || obj.getSituacao() == 1){
            venda.setSituacao(obj.getSituacao());
        } else {
            throw new DataBindingViolationException("Entrada não aceita do parametro de 'Situação'");
        }
        
        return venda;
    }

}
