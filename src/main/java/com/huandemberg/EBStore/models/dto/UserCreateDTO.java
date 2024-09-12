package com.huandemberg.EBStore.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String username;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @NotBlank
    @Size(min = 4, max = 255)
    private String nome;

    @NotBlank
    @Size(min = 1, max = 255)
    private String email;

    @NotBlank
    @Size(min = 10, max = 15)
    private String dataNasc;

    @NotBlank
    @Size(min = 14, max = 14)
    private String cpf;

}
