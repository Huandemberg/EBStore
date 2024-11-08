package com.huandemberg.EBStore.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendaUpdateDTO {

    private Long id;

    private Float valorCliente;

    @NotBlank
    @Size(min = 1, max = 50)
    private String formPag;

    private Long cliente_Id;

    private Long produto_Id;

    private int estoque;
}
