package com.example.desafio_spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio_spring.dtos.BoletoResponseDto;
import com.example.desafio_spring.models.BoletoModelo;
import com.example.desafio_spring.services.BoletoService;


import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/boleto")
@AllArgsConstructor
public class BoletoController {

    @Autowired
    private BoletoService boletoService;


    @GetMapping("gerar")
    public GeradorDeBoleto geraBoleto() {
        return boletoService.geradorDeBoletoPdf();
    }

    @GetMapping()
    public List<BoletoModelo> buscarBoletos() {
        return boletoService.buscarBoletos();
    }

    @PostMapping()
    public BoletoModelo criarBoleto(@RequestBody BoletoModelo boleto) {
        return boletoService.criarBoleto(boleto);
    }

    @GetMapping("/{id}")
    public Optional<BoletoModelo> buscarBoletoPorId(@PathVariable Long id) {
        return boletoService.buscarBoletoPorId(id);
    }

    @PostMapping("/{id}/pagar")
    public BoletoResponseDto pagarBoleto(@PathVariable Long id) {
        return boletoService.pagarBoleto(id);
    }

    @PostMapping("/{id}/cancelar")
    public BoletoResponseDto cancelarBoleto(@PathVariable Long id) {
        return boletoService.cancelarBoleto(id);
    }

}
