package com.example.desafio_spring.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.desafio_spring.controllers.AccessTokenConrtoller;
import com.example.desafio_spring.controllers.CobrancaController;
import com.example.desafio_spring.models.BoletoRegistrado;
import com.example.desafio_spring.models.CobrancaInput;
import com.example.desafio_spring.models.CobrancaModel;
import com.example.desafio_spring.models.DescontoInput;
import com.example.desafio_spring.models.Fatura;
import com.example.desafio_spring.models.FaturaRegistrada;
import com.example.desafio_spring.models.JurosInput;
import com.example.desafio_spring.models.MultaInput;
import com.example.desafio_spring.models.PagadorInput;
import com.example.desafio_spring.models.Pessoa;
import com.example.desafio_spring.repositories.FaturaRegistradaRepository;
import com.example.desafio_spring.repositories.FaturaRepository;
import com.example.desafio_spring.util.NormalizadorUtil;

import jakarta.transaction.Transactional;

@Service
public class FaturaService {

	@Autowired
	private FaturaRepository repository;

	@Autowired
	private AccessTokenConrtoller accessTokenController;

	@Autowired
	private CobrancaController cobrancaController;

	@Autowired
	private FaturaRegistradaRepository faturaRegistradaRepository;

	@Autowired
	private GeradorDeBoletoService geradorBoleto;

	public byte[] gerar(Long faturaId) {

		var fatura = repository.getOne(faturaId);
		var cobranca = transformarFaturaEmCobranca(faturaId);

		return geradorBoleto.gerar(fatura, cobranca);
	}

	@Transactional
	public BoletoRegistrado registrarCobranca(Long faturaId, CobrancaModel cobranca) {
		var token = accessTokenController.requisitarToken(cobranca.getClientId(), cobranca.getClientSecret());
		var boletoRegistrado = cobrancaController.registrar(transformarFaturaEmCobranca(faturaId), token,
				cobranca.getAppKey());

		var fatura = repository.getOne(faturaId);
		fatura.setNossoNumero(Long.valueOf(boletoRegistrado.getNumero()).toString());

		// estamos persistindo a fatura registrada
		var faturaRegistrada = new FaturaRegistrada().criar(fatura, boletoRegistrado.getLinhaDigitavel(),
				boletoRegistrado.getQrCode().getUrl(), boletoRegistrado.getQrCode().getEmv());
		faturaRegistradaRepository.save(faturaRegistrada);

		return boletoRegistrado;
	}

	public CobrancaInput transformarFaturaEmCobranca(Long faturaId) {

		var fatura = repository.getOne(faturaId);

		return criar(fatura);
	}

	public CobrancaInput criar(Fatura fatura) {

		var builder = CobrancaInput.builder();

		var desconto = DescontoInput.builder().tipo(0).build();
		var juros = JurosInput.builder().tipo(2).porcentagem(fatura.getConvenio().getJurosPorcentagem())
				.valor(BigDecimal.ZERO).build();

		var multa = MultaInput.builder().tipo(2).data(converterData(fatura.getDataVencimento().plusDays(1)))
				.porcentagem(fatura.getConvenio().getMultaPorcentagem()).valor(BigDecimal.ZERO).build();

		var pessoa = fatura.getPessoa();

		var pagador = PagadorInput.builder().tipoInscricao(pessoa.isPessoaFisica() ? 1 : 2)
				.numeroInscricao(pessoa.getDocumento()).nome(NormalizadorUtil.norm(pessoa.getNome()))
				.cep(Long.valueOf(pessoa.getEndereco().getCep().replaceAll("\\D", "")))
				.cidade(NormalizadorUtil.norm(pessoa.getEndereco().getCidade()))
				.bairro(NormalizadorUtil.abreviar(NormalizadorUtil.norm(pessoa.getEndereco().getBairro())))
				.uf(pessoa.getEndereco().getUf()).endereco(criarEnderecoCompleto(pessoa, 40)).build();

		builder.numeroConvenio(Long.valueOf(fatura.getConvenio().getNumeroContrato()));
		builder.numeroCarteira(Integer.valueOf(fatura.getConvenio().getCarteira()));
		builder.numeroVariacaoCarteira(Integer.valueOf(fatura.getConvenio().getVariacaoCarteira()));
		builder.dataVencimento(converterData(fatura.getDataVencimento()));
		builder.dataEmissao(converterData(LocalDate.now()));
		builder.valorOriginal(fatura.getValor());
		builder.indicadorAceiteTituloVencido("S"); // S/N
		builder.codigoAceite('N');
		builder.codigoTipoTitulo(2);
		builder.descricaoTipoTitulo("Duplicata Mercantil");
		builder.indicadorPermissaoRecebimentoParcial('N');
		builder.numeroTituloBeneficiario(fatura.getNumeroDocumento());
		builder.numeroTituloCliente(criarNossoNumero(fatura));
		builder.indicadorPix("S");
		builder.desconto(desconto);
		builder.jurosMora(juros);
		builder.multa(multa);
		builder.pagador(pagador);

		return builder.build();
	}

	private String converterData(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	private String criarNossoNumero(Fatura fatura) {
		// regra: "000" + numero contrato convenio + 10 algarismos com zeros a esquerda
		// utilizar como 10 algarismos o numero documento

		return String.format("%010d", Long.valueOf(fatura.getConvenio().getNumeroContrato()))
				.concat(String.format("%010d", Long.valueOf(fatura.getNumeroDocumento())));
	}

	private String criarEnderecoCompleto(Pessoa pessoa, int tamanhoMaximoEndereco) {
		var enderecoCompleto = "";

		var endereco = pessoa.getEndereco();
		var logradouro = NormalizadorUtil.abreviar(NormalizadorUtil.norm(endereco.getLogradouro())).concat(", ");
		var temComplemento = StringUtils.hasText(endereco.getComplemento());
		var tamanhoDoNumero = endereco.getNumero().length();
		var tamanhoDoLogradouro = logradouro.length();

		if (temComplemento) {
			tamanhoDoNumero += 2; // conta-se a virgula e o espaco

			var tamanhoTotal = tamanhoDoNumero + tamanhoDoLogradouro + endereco.getComplemento().length();

			if (tamanhoTotal > tamanhoMaximoEndereco) {

				logradouro = logradouro.substring(0, (tamanhoTotal - tamanhoMaximoEndereco));

				enderecoCompleto = logradouro
						.concat(endereco.getNumero().concat(", ").concat(endereco.getComplemento()));
			}

		} else {

			var tamanhoTotal = tamanhoDoNumero + tamanhoDoLogradouro;

			if (tamanhoTotal > tamanhoMaximoEndereco) {
				logradouro = logradouro.substring(0, (tamanhoTotal - tamanhoMaximoEndereco));

				enderecoCompleto = logradouro.concat(endereco.getNumero());
			} else {
				enderecoCompleto = logradouro.concat(endereco.getNumero());
			}
		}

		return enderecoCompleto;
	}

}