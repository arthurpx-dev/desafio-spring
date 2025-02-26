package com.example.desafio_spring.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Endereco {

	@Column(name = "endereco_logradouro")
	private String logradouro;

	@Column(name = "endereco_numero")
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@Column(name = "endereco_bairro")
	private String bairro;

	@Column(name = "endereco_cidade")
	private String cidade;

	@Column(name = "endereco_uf")
	private String uf;

	@Column(name = "endereco_cep")
	private String cep;

}