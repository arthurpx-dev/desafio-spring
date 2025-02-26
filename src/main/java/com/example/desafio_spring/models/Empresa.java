package com.example.desafio_spring.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Empresa extends BaseEntity {

	private String razaoSocial;
	private String cnpj;
	
	@Embedded
	private Endereco endereco;
	
}