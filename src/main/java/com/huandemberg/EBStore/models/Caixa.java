package com.huandemberg.EBStore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Caixa.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Caixa {
    
    public static final String TABLE_NAME = "caixa";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "valorCaixa", nullable = false)
    private float valorCaixa;

    public void reduzirValor(float valor){
        this.setValorCaixa(this.getValorCaixa() - valor);
    }
    
    public void addValor(float valor) {
        this.setValorCaixa(this.getValorCaixa() + valor);
    }

}
