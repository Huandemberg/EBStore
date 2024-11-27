package com.huandemberg.EBStore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Produto.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto {

    public static final String TABLE_NAME = "produto";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "modelo", nullable = false)
    @NotBlank
    @Size(min = 10, max = 255)
    private String modelo;

    @Column(name = "tamanho", length = 5, nullable = false)
    @NotBlank
    @Size(min = 1, max = 5)
    private String tamanho;

    @Column(name = "cor", length = 20, nullable = false)
    @NotBlank
    @Size(min = 3, max = 20)
    private String cor;

    @Column(name = "preco", nullable = false)
    @NotNull
    private float preco;

    @Column(name = "estoque", nullable = false)
    @NotNull
    private int estoque;

    public void reduzirEstoque(int i) {
        this.setEstoque(this.getEstoque() - i);
    }
}
