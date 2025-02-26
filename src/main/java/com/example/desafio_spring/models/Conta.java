package com.example.desafio_spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conta extends BaseEntity {
    private String agencia;
	private String conta;
	private String digitoAgencia;
	private String digitoConta;
	
	@ManyToOne
	@JoinColumn(name = "banco_id")
	private Banco banco;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
}
