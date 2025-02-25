package com.example.desafio_spring.models;

import java.time.LocalDate;

import com.example.desafio_spring.enums.BoletoStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "Boleto")
@Data
public class BoletoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valor;
    private BoletoStatusEnum status;

    
}
