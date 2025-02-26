package com.example.desafio_spring.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio_spring.models.CobrancaInput;
import com.example.desafio_spring.models.Fatura;
import com.example.desafio_spring.repositories.FaturaRegistradaRepository;

import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

@Service
public class PdfBoletoFileService implements GeradorDeBoletoService {

	@Autowired
	private GeradorQrCodeService geradorQrcode;
	
	@Autowired
	private FaturaRegistradaRepository faturaRegistradaRepository;
	
	@Override
	public byte[] gerar(Fatura fatura, CobrancaInput cobranca) {

		var boleto = criarBoleto(fatura, cobranca);
		
		var pathBoletoPersonalizado = this.getClass().getResourceAsStream("/relatorios/boleto-personalizado.jasper");
		var pathLogo = this.getClass().getResourceAsStream("/relatorios/logo-curso-api-bb.png");
		var faturaRegistrada = faturaRegistradaRepository.buscarPorFaturaId(fatura.getId());
		
		var parametros = new HashMap<String, Object>();
		parametros.put("QRCODE", geradorQrcode.gerar(faturaRegistrada.getQrcodeEmv()));
		parametros.put("LOGO", pathLogo);
		

		var gerador = new GeradorDeBoleto(pathBoletoPersonalizado, parametros, boleto);

		return gerador.geraPDF();
	}

}