package com.example.desafio_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio_spring.models.BoletoRegistrado;
import com.example.desafio_spring.models.CobrancaInput;
import com.example.desafio_spring.models.CobrancaModel;
import com.example.desafio_spring.services.FaturaService;

@RestController
@RequestMapping("faturas")
public class FaturaController {

	@Autowired
	private FaturaService service;

	@GetMapping("{faturaId}")
	public CobrancaInput transformar(@PathVariable Long faturaId) {
		return service.transformarFaturaEmCobranca(faturaId);
	}

	@PostMapping("{faturaId}")
	public BoletoRegistrado registrar(@PathVariable Long faturaId, @RequestBody CobrancaModel model) {
		return service.registrarCobranca(faturaId, model);
	}

	@GetMapping(path = "{faturaId}/boleto/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> gerarBoleto(@PathVariable Long faturaId) {
		byte[] bytesPdf = service.gerar(faturaId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytesPdf);
	}

}