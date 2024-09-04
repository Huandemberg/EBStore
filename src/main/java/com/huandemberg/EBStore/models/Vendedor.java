package com.huandemberg.EBStore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Vendedor.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor extends Pessoa {
    public static final String TABLE_NAME = "vendedor";

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @Column(name = "situacao", nullable = false)
    @NotBlank
    private boolean situacao;



}
