package com.huandemberg.EBStore.models.projection;

import com.huandemberg.EBStore.models.Venda;

public interface ClienteDebitoProjection {
    
    public Venda getVenda();

    public Double getTotalDebito();


}
