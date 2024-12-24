package com.huandemberg.EBStore.models.projection;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.User;
import com.huandemberg.EBStore.models.convert.ListToJsonConverter;

import jakarta.persistence.Convert;


public interface VendaProjection {

    public Long getId();

    public Produto getProduto();

    public Float getValorCliente();

    public String getFormPag();

    public String getData();

    @JsonIgnoreProperties({"situacao", "username", "cpf", "dataNasc", "email"})
    public User getUser();

    @JsonIgnoreProperties({"email", "dataNasc", "cpf", "user", "email"})
    public Cliente getCliente();

    @Convert(converter = ListToJsonConverter.class)
    public List<Integer> getQuantidade();

}
