package com.example.desafio_spring.services;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import com.example.desafio_spring.models.CobrancaInput;
import com.example.desafio_spring.models.Fatura;


import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;


public interface GeradorDeBoletoService {

	public byte[] gerar(Fatura fatura, CobrancaInput cobranca);
	
	
	default Boleto criarBoleto(Fatura fatura, CobrancaInput cobranca) {
		
		var instrucao1 = String.format("Após o vencimento cobrar Juros de %s por mês.", cobranca.getJurosMora().getPorcentagem());
		var instrucao2 = String.format("Após o vencimento cobrar Multa de %s.", cobranca.getMulta().getPorcentagem());
		var instrucao3 = String.format("Referente a Fatura nº: %d", fatura.getId());
		
		var boleto = Boleto.novoBoleto()
				.comBanco(new BancoDoBrasil())
				.comDatas(criarDatas(fatura))
				.comBeneficiario(criarBeneficiario(fatura))
				.comPagador(criarPagador(fatura, cobranca))
				.comValorBoleto(fatura.getValor())
				.comNumeroDoDocumento(fatura.getNumeroDocumento())
				.comEspecieDocumento("DM")
				.comAceite(false)
				.comLocaisDePagamento("Pagável em qualquer Banco até o vencimento")
				.comInstrucoes(instrucao1, instrucao2, instrucao3);
		
		return boleto;
	}
	
	default Datas criarDatas(Fatura fatura) {
		
		var vencimento = fatura.getDataVencimento();
		var criadoEm = fatura.getCriadoEm();
		var atualizadoEm = fatura.getAtualizadoEm();
		
		var datas = Datas.novasDatas()
				.comDocumento(criadoEm.getDayOfMonth(), criadoEm.getMonthValue(), criadoEm.getYear())
				.comProcessamento(atualizadoEm.getDayOfMonth(), atualizadoEm.getMonthValue(), atualizadoEm.getYear())
				.comVencimento(vencimento.getDayOfMonth(), vencimento.getMonthValue(), vencimento.getYear());
		
		return datas;
	}
	
	default Beneficiario criarBeneficiario(Fatura fatura) {
		
		var empresa = fatura.getConta().getEmpresa();
		var conta = fatura.getConta();
		var endereco = Endereco.novoEndereco()
				.comLogradouro(empresa.getEndereco().getLogradouro().concat(", ").concat(empresa.getEndereco().getNumero()))
				.comBairro(empresa.getEndereco().getBairro())
				.comCep(insereMascaraAoRetornarDocumento(empresa.getEndereco().getCep().replaceAll("\\D", "")))
				.comCidade(empresa.getEndereco().getCidade())
				.comUf(empresa.getEndereco().getUf());
		
		var beneficiario = Beneficiario.novoBeneficiario()
				.comNomeBeneficiario(empresa.getRazaoSocial())
				.comDocumento(insereMascaraAoRetornarDocumento(empresa.getCnpj()))
				.comNossoNumero(fatura.getNossoNumero())
				.comAgencia(conta.getAgencia())
				.comDigitoAgencia(conta.getDigitoAgencia())
				.comCodigoBeneficiario(conta.getConta())
				.comDigitoCodigoBeneficiario(conta.getDigitoConta())
				.comNumeroConvenio(fatura.getConvenio().getNumeroContrato())
				.comCarteira(fatura.getConvenio().getCarteira())
				.comEndereco(endereco);
		
		return beneficiario;
	}
	
	default Pagador criarPagador(Fatura fatura, CobrancaInput cobranca) {
		
		var endereco = Endereco.novoEndereco()
				.comLogradouro(cobranca.getPagador().getEndereco())
				.comBairro(cobranca.getPagador().getBairro())
				.comCep(insereMascaraAoRetornarDocumento(cobranca.getPagador().getCep().toString()).concat(" "))
				.comCidade(cobranca.getPagador().getCidade())
				.comUf(cobranca.getPagador().getUf());
		
		var pagador = Pagador.novoPagador()
				.comNome(cobranca.getPagador().getNome())
				.comDocumento(insereMascaraAoRetornarDocumento(cobranca.getPagador().getNumeroInscricao()))
				.comEndereco(endereco);
		
		return pagador;
	}
	
	default String insereMascaraAoRetornarDocumento(String documento) {
		try {
			MaskFormatter mask = new MaskFormatter();
			mask.setValueContainsLiteralCharacters(false);
			if (documento.length() == 11) {
				mask.setMask("###.###.###-##");
			} else if(documento.length() == 8) {
				mask.setMask("##.###-###");
			} else {
				mask.setMask("###.###.###/####-##");
			}
			return mask.valueToString(documento);
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao formatar documento.");
		}
	}
	
}