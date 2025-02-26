package com.example.desafio_spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FaturaRegistrada extends BaseEntity {

	private String linhaDigitavel;
	private String qrcodeUrl;
	private String qrcodeEmv;
	
	@ManyToOne
	@JoinColumn(name = "fatura_id")
	private Fatura fatura;
	
	
	public FaturaRegistrada criar(Fatura fatura, String linhaDigitavel, String qrcodeUrl, String qrcodeEmv) {
		this.linhaDigitavel = linhaDigitavel;
		this.qrcodeUrl = qrcodeUrl;
		this.qrcodeEmv = qrcodeEmv;
		this.fatura = fatura;
		
		return this;
	}
	
}