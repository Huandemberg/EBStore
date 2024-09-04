package com.huandemberg.EBStore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Cliente.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Pessoa{
    public static final String TABLE_NAME = "cliente";
}
