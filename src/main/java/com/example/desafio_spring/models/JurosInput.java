package com.example.desafio_spring.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JurosInput {
    private Integer tipo;
	private BigDecimal porcentagem;
	private BigDecimal valor;
	
}
