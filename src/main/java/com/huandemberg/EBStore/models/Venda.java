package com.huandemberg.EBStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huandemberg.EBStore.models.convert.ListToJsonConverter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Venda.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venda {

    public static final String TABLE_NAME = "venda";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "produto_venda",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produto;

    @Column(name = "valorCliente", nullable = false)
    private float valorCliente;

    @Column(name = "formPag", nullable = false)
    @NotBlank
    @Size(min = 1, max = 50)
    private String formPag;

    @Column(name = "data", length = 15, nullable = false)
    @NotBlank
    @Size(min = 10, max = 15)
    private String data;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false, updatable = false)
    @JsonIgnoreProperties({"situacao", "username", "cpf", "dataNasc", "email"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, updatable = true)
    @JsonIgnoreProperties({"email", "dataNasc", "cpf", "user", "email"})
    private Cliente cliente;
   
    @Column(name = "quantidade", nullable = false, columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
    @NotNull
    private List<Integer> quantidade;

    @Column(name = "situacao", nullable = false)
    private int situacao;

    @ManyToOne
    @JoinColumn(name = "caixa_id", nullable = true)
    private Caixa caixa;

}
