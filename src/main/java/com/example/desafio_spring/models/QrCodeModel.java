package com.example.desafio_spring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrCodeModel {

	private String url;
	private String txId;
	private String emv;
	
}
