package com.huandemberg.EBStore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false, updatable = true)
    private Produto produto;

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

}
