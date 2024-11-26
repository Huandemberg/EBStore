package com.huandemberg.EBStore.models.projection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.User;


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

    public int getQuantidade();

}
