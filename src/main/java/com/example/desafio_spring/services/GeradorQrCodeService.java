package com.example.desafio_spring.services;

import java.io.InputStream;

public interface GeradorQrCodeService {

	InputStream gerar(String codigo);
	
}