package com.example.desafio_spring.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banco extends BaseEntity {
    private String codigo;
    private String nome;
}
