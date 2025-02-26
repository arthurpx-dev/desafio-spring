package com.example.desafio_spring.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.desafio_spring.enums.SituacaoFaturaEnum;
import com.example.desafio_spring.enums.TipoFaturaEnum;
import com.example.desafio_spring.enums.TipoPagamentoEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Fatura extends BaseEntity {

	private BigDecimal valor;
	private LocalDate dataVencimento;

	@Enumerated(EnumType.STRING)
	private TipoFaturaEnum tipo;

	@Enumerated(EnumType.STRING)
	private TipoPagamentoEnum tipoPagamento;

	@Enumerated(EnumType.STRING)
	private SituacaoFaturaEnum situacao;

	private String numeroDocumento;
	private String nossoNumero;

	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "convenio_id")
	private Convenio convenio;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

}