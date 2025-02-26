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
public class MultaInput {

	private Integer tipo;
	private String data;
	private BigDecimal porcentagem;
	private BigDecimal valor;

}