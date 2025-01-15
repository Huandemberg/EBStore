package com.huandemberg.EBStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Transacao.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transacao {

    public static final String TABLE_NAME = "transacao";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({ "username", "cpf", "dataNasc", "email"})
    private List<Venda> vendas;

    @Column(name = "descricao", nullable = false)
    @NotBlank
    @Size(min = 8, max = 360)
    private String descricao;

    @Column(name = "valorTransacao", nullable = true)
    private float valorTransacao;

    //1 para recebimento(entrada do caixa) e 0 para pagamento(saida do caixa)
    @Column(name = "tipoTransacao", nullable = false)
    private int tipoTransacao;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnoreProperties({"situacao", "username", "cpf", "dataNasc", "email"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "caixa_id", nullable = false, updatable = true)
    private Caixa caixa;

    public void calcTransacao(List<Venda> vendas) {
        for (Venda venda : vendas) {

            this.setValorTransacao(venda.getValorCliente() +  this.getValorTransacao());

        }
    }

}
