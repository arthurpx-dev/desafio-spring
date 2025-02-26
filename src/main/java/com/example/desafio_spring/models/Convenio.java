package com.example.desafio_spring.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Convenio extends BaseEntity {

	private String numeroContrato;
	private String carteira;
	private String variacaoCarteira;
	private BigDecimal jurosPorcentagem;
	private BigDecimal multaPorcentagem;
	
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;
	
}