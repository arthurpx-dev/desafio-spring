package com.example.desafio_spring.dtos;

import java.time.LocalDate;

import com.example.desafio_spring.enums.BoletoStatusEnum;

import lombok.Data;

@Data
public class BoletoDto {

    private String codigo;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valor;
    private BoletoStatusEnum status;

}
