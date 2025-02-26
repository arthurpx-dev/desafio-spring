package com.example.desafio_spring.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pessoa extends BaseEntity {

	private String nome;
	private String documento;
	private boolean pessoaFisica;
	
	@Embedded
	private Endereco endereco;
	
}
