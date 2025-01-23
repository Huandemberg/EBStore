package com.huandemberg.EBStore.models.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransacaoCreateDTO {
    
    private List<Long> vendas_id;

    @Column(name = "descricao", nullable = false)
    @NotBlank
    @Size(min = 8, max = 360)
    private String descricao;

    private Long caixa;

}
