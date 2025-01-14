package com.huandemberg.EBStore.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huandemberg.EBStore.models.Transacao;
import com.huandemberg.EBStore.models.User;
import com.huandemberg.EBStore.models.Venda;
import com.huandemberg.EBStore.models.enums.ProfileEnum;
import com.huandemberg.EBStore.repositories.TransacaoRepository;
import com.huandemberg.EBStore.security.UserSpringSecurity;
import com.huandemberg.EBStore.services.exceptions.AuthorizationException;
import com.huandemberg.EBStore.services.exceptions.DataBindingViolationException;
import com.huandemberg.EBStore.services.exceptions.ObjectNotFoundException;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VendaService vendaService;

    public Transacao findById(Long id) {
        Transacao transacao = this.transacaoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Transacao.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTransacao(userSpringSecurity, transacao))
            throw new AuthorizationException("Acesso negado!");

        return transacao;

    }

    public List<Transacao> findAll() {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<Transacao> transacoes = this.transacaoRepository.findAll();
        return transacoes;

    }

    @Transactional
    public Transacao create(Transacao obj) {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado!");
        }

        User user = this.userService.findById(userSpringSecurity.getId());
        List<Venda> vendas = obj.getVendas();
        obj.calcTransacao(vendas);
        if (obj.getTipoTransacao() != 1 && obj.getTipoTransacao() != 0) {

            throw new DataBindingViolationException(
                    "Formato de entrada da requisição incompativel com o campo 'Tipo de transação' ");

        }
        obj.setId(null);
        obj.setUser(user);
        for (Venda venda : vendas) {
            this.vendaService.updateBaixa(venda);
        }
        obj = this.transacaoRepository.save(obj);
        return obj;

    }



    private Boolean userHasTransacao(UserSpringSecurity userSpringSecurity, Transacao transacao) {
        return transacao.getUser().getId().equals(userSpringSecurity.getId());
    }

}
