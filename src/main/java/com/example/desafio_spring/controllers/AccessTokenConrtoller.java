package com.example.desafio_spring.controllers;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Base64;
import org.springframework.web.client.RestTemplate;


import com.example.desafio_spring.models.AccessTokenBB;

@Component
public class AccessTokenConrtoller {

	private RestTemplate restTemplate = new RestTemplate();
	
	public String requisitarToken(String clientId, String clientSecret) {
		
		URI uri = URI.create("https://oauth.hm.bb.com.br/oauth/token");
		var headers = new HttpHeaders();
		
		var basic = clientId.concat(":").concat(clientSecret);
		byte[] auth = Base64.getEncoder().encode(basic.getBytes());
		
		headers.set("Authorization", "Basic " + new String(auth));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "client_credentials");
		map.add("scope", "cobrancas.boletos-requisicao");
		
		
		var request = new HttpEntity<>(map, headers);
		
		var accessToken = restTemplate.postForObject(uri, request, AccessTokenBB.class);
		
		return accessToken.getAccess_token();
	}
	
}