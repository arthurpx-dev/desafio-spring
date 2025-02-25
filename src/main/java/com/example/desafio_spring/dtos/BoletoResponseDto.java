package com.example.desafio_spring.dtos;

import com.example.desafio_spring.enums.BoletoResponseEnum;

import lombok.Data;

@Data
public class BoletoResponseDto {
    private String descricao;
    private int statusCode;

    public void responseDto(BoletoResponseEnum boletoResponseEnum) {
        this.descricao = boletoResponseEnum.getDescricao();
        this.statusCode = boletoResponseEnum.getHttpStatus().value();
    }

}
