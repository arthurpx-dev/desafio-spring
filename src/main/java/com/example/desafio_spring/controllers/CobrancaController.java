package com.example.desafio_spring.controllers;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.desafio_spring.models.BoletoRegistrado;
import com.example.desafio_spring.models.CobrancaInput;

@Component
public class CobrancaController {

	private RestTemplate restTemplate = new RestTemplate();
	
	public BoletoRegistrado registrar(CobrancaInput cobranca, String token, String key) {
		URI uri = URI.create("https://api.hm.bb.com.br/cobrancas/v2/boletos");
		var uriBuilder = UriComponentsBuilder.fromUri(uri);
		uriBuilder.queryParam("gw-dev-app-key", key);
		
		var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		
		var request = new HttpEntity<>(cobranca, headers);
		
		var response = restTemplate.postForObject(uriBuilder.build().toUri(), request, BoletoRegistrado.class);
		
		return response;
	}
	
}