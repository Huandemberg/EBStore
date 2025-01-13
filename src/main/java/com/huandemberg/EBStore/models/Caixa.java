package com.huandemberg.EBStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Caixa.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Caixa {

    public static final String TABLE_NAME = "caixa";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "username", "cpf", "dataNasc", "email"})
    private List<Venda> vendas;

    @Column(name = "descricao", nullable = false)
    @NotBlank
    @Size(min = 8, max = 360)
    private String descricao;

    @Column(name = "valorTransacao", nullable = false)
    private float valorTransacao;

    @Column(name = "tipoTransacao", nullable = false)
    private int tipoTransacao;

    @Column(name = "valorCaixa", nullable = false)
    private float valorCaixa;

    public void calcTransacao(List<Venda> vendas) {
        for (Venda venda : vendas) {

            this.valorTransacao = venda.getValorCliente() + this.valorTransacao;

        }
    }

}
