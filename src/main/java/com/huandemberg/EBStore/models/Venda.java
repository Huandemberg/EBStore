package com.huandemberg.EBStore.models;

import java.util.List;

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
    private Float valorCliente;

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
    private User user;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, updatable = true)
    private Cliente cliente;

    @Column(name = "quantidade", nullable = false)
    @NotNull
    private int quantidade;

}
