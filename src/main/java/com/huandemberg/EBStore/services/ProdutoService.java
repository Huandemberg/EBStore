package com.huandemberg.EBStore.services;

import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.models.projection.ProdutoProjection;
import com.huandemberg.EBStore.repositories.ProdutoRepository;
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
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto findById(Long id){
        Produto produto = this.produtoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Produto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()
        ));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN))
            throw new AuthorizationException("Acesso negado!");

        return produto;
    }

    public List<ProdutoProjection> findAllByProdutos(){

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<ProdutoProjection> produtos = this.produtoRepository.findByAllProdutos();
        return produtos;
    }

    public List<Produto> findAllByIds(List<Long> produtoIds) {
        return this.produtoRepository.findAllByIds(produtoIds);
    }

    public List<Produto> findAllByModeloLike(String name){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        String pattern = "%" + name + "%";
        List<Produto> produtos = this.produtoRepository.findAllByModeloLike(pattern);
        return produtos;
    }

    @Transactional
    public Produto create(Produto obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        obj = this.produtoRepository.save(obj);
        return obj;
    }

    @Transactional
    public Produto update(Produto obj) {
        Produto newObj = findById(obj.getId());
        newObj.setModelo(obj.getModelo());
        newObj.setCor(obj.getCor());
        newObj.setPreco(obj.getPreco());
        newObj.setTamanho(obj.getTamanho());
        newObj.setEstoque(obj.getEstoque());
        return this.produtoRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }


}
