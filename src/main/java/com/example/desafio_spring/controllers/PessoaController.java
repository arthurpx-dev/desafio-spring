package com.example.desafio_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio_spring.models.Pessoa;
import com.example.desafio_spring.repositories.PessoaRepository;

@RequestMapping("pessoas")
@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository repository;
	
	@GetMapping
	public List<Pessoa> listar() {
		return repository.findAll();
	}
	
}