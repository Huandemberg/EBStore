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

    @Autowired
    private CaixaService caixaService;

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
    public Transacao createIncrement(Transacao obj) {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado!");
        }

        User user = this.userService.findById(userSpringSecurity.getId());
        List<Venda> vendas = obj.getVendas();
        Transacao newObj = new Transacao();
        obj.setTipoTransacao(1);
        obj.calcTransacao(vendas);
        this.caixaService.updateIncrementar(obj.getCaixa(), obj.getValorTransacao());
        obj.setId(newObj.getId());
        obj.setUser(user);
        for (Venda venda : vendas) {
            this.vendaService.updateBaixa(venda);
        }
        obj = this.transacaoRepository.save(obj);
        return obj;

    }

    @Transactional
    public Transacao createDecrement(Transacao obj) {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Acesso negado!");
        }

        User user = this.userService.findById(userSpringSecurity.getId());
        List<Venda> vendas = obj.getVendas();
        obj.calcTransacao(vendas);
/*         // Tipo de transação(1) para adicionar ao caixa, efetivamente venda
        if (Integer.valueOf(1).equals(obj.getTipoTransacao())) {

            this.caixaService.updateIncrementar(obj.getCaixa(), obj.getValorTransacao());

        } // Tipo de transação(0) para deduzir do caixa, efetivamente contas a pagar
        if (Integer.valueOf(0).equals(obj.getTipoTransacao())) {

            this.caixaService.updateDecrementar(obj.getCaixa(), obj.getValorTransacao());

        } else {

            throw new DataBindingViolationException(
                    "Formato de entrada da requisição incompativel com o campo 'Tipo de transação' " + obj.getTipoTransacao() + "tipo de dado: " + ((Object) obj.getTipoTransacao()).getClass());

        } */
        obj.setTipoTransacao(0);
        this.caixaService.updateDecrementar(obj.getCaixa(), obj.getValorTransacao()); obj.getValorTransacao();
        obj.setId(null);
        obj.setUser(user);
        for (Venda venda : vendas) {
            this.vendaService.updateBaixa(venda);
        }
        obj = this.transacaoRepository.save(obj);
        return obj;

    }

    public Transacao updateExclusao(Transacao obj) {
        Transacao newObj = findById(obj.getId());
        /*
         * Para exclusão e reversão das alterações do caixa realizei a inversão da
         * lógica(1 para deduzir do caixa e 0 para incrementar)
         */
        if (obj.getTipoTransacao() == 1) {
            this.caixaService.updateDecrementar(obj.getCaixa(), obj.getValorTransacao());
        } else {
            this.caixaService.updateIncrementar(obj.getCaixa(), obj.getValorTransacao());
        }
        List<Venda> vendas = obj.getVendas();
        for (Venda venda : vendas) {
            this.vendaService.updateReabertura(venda);
        }
        return this.transacaoRepository.save(newObj);
    }

    public void delete(Long id) {
        try {
            updateExclusao(findById(id));
            this.transacaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException(e.getMessage());
        }
    }

    private Boolean userHasTransacao(UserSpringSecurity userSpringSecurity, Transacao transacao) {
        return transacao.getUser().getId().equals(userSpringSecurity.getId());
    }

}
