package com.huandemberg.EBStore.models.projection;

import com.huandemberg.EBStore.models.Cliente;
import com.huandemberg.EBStore.models.Produto;
import com.huandemberg.EBStore.models.User;

public interface VendaProjection {

    public Long getId();

    public Produto getProduto();

    public Float getValorCliente();

    public String getFormPag();

    public String getData();

    public User getUser();

    public Cliente gerCliente();

}
