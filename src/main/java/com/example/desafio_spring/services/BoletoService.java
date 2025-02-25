package com.example.desafio_spring.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio_spring.dtos.BoletoResponseDto;
import com.example.desafio_spring.enums.BoletoResponseEnum;
import com.example.desafio_spring.enums.BoletoStatusEnum;
import com.example.desafio_spring.models.BoletoModelo;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Modalidade;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

import com.example.desafio_spring.repositories.BoletoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoletoService {

    @Autowired
    private BoletoRepository boletoRepository;


    public GeradorDeBoleto geradorDeBoletoPdf(){
        return new GeradorDeBoleto(gerarBoleto());
    }

    public Boleto gerarBoleto() {
        Beneficiario beneficiario = gerarBeneficiario();
        Pagador pagador = gerarPagador();
        
        Banco banco = new BancoDoBrasil(); 
        Boleto boleto = Boleto.novoBoleto()
                .comValorBoleto(new BigDecimal("150.00"))
                .comEspecieMoeda("R$")
                .comCodigoEspecieMoeda(9)
                .comAceite(true)
                .comEspecieDocumento("DV") 
                .comNumeroDoDocumento("123456") 
                .comDatas(
                        Datas.novasDatas()
                                .comDocumento(1, 5, 2013)
                                .comProcessamento(1, 5, 2013)
                                .comVencimento(2, 5, 2013))
                .comPagador(pagador)
                .comBeneficiario(beneficiario)
                .comBanco(banco)
                .comInstrucoes("Instrução 1", "Instrução 2")
                .comLocaisDePagamento("Banco X", "Agência Y")
                .comQuantidadeMoeda(BigDecimal.valueOf(2.0))
                .comValorMoeda(BigDecimal.valueOf(1.0));
    
        banco.geraCodigoDeBarrasPara(boleto); 
    
        return boleto;
    }
    

    private Pagador gerarPagador() {
        return Pagador.novoPagador()
                .comNome("Arthur")
                .comDocumento("123.456.789-01")
                .comEndereco(gerarEndereco());
    }

    private Endereco gerarEndereco() {
        return Endereco.novoEndereco()
                .comLogradouro("Rua dos Bobos, nº 0")
                .comBairro("Bairro Feliz")
                .comCep("12345-678")
                .comCidade("São Paulo")
                .comUf("SP");
    }

    private Beneficiario gerarBeneficiario() {
        return Beneficiario.novoBeneficiario()
            .comNomeBeneficiario("Nome do Beneficiário")
            .comAgencia("6501") // Código da agência Matriz-São Paulo
            .comDigitoAgencia("X") // Dígito da agência, se houver
            .comCodigoBeneficiario("1234567") // Código do beneficiário (número da conta)
            .comDigitoCodigoBeneficiario("Y") // Dígito do código do beneficiário
            .comCarteira("18") // Código da carteira (18 = com registro)
            .comNossoNumero("1234567890") // Nosso número
            .comDigitoNossoNumero("Z") // Dígito do nosso número
            .comDocumento("00.000.000/0000-00") // CNPJ do beneficiário
            .comEndereco(gerarEndereco())
            .comNumeroConvenio("1234567") // Número do convênio
            .comModalidade(Modalidade.COM_REGISTRO); // Modalidade registrada
    }
    

    public List<BoletoModelo> buscarBoletos() {
        return boletoRepository.findAll();
    }

    public Optional<BoletoModelo> buscarBoletoPorId(Long id) {
        Optional<BoletoModelo> boleto = boletoRepository.findById(id);
        return boleto;
    }

    public BoletoModelo criarBoleto(BoletoModelo boleto) {
        boleto.setStatus(BoletoStatusEnum.ABERTO);
        return boletoRepository.save(boleto);
    }

    public BoletoResponseDto pagarBoleto(Long id) {
        Optional<BoletoModelo> boleto = boletoRepository.findById(id);
        BoletoResponseDto responseDto = new BoletoResponseDto();
        LocalDate hoje = LocalDate.now();
        if (boleto.isEmpty()) {
            responseDto.responseDto(BoletoResponseEnum.NAO_LOCALIZADO);
            return responseDto;
        }
        if (boleto.get().getStatus().equals(BoletoStatusEnum.CANCELADO)) {
            responseDto.responseDto(BoletoResponseEnum.PROIBIDO_PAGAR);
            return responseDto;
        }
        try {
            boleto.get().setStatus(BoletoStatusEnum.PAGO);
            boleto.get().setDataPagamento(hoje);
            boletoRepository.save(boleto.get());
            responseDto.responseDto(BoletoResponseEnum.BOLETO_PAGO);
            return responseDto;
        } catch (Exception e) {
            responseDto.responseDto(BoletoResponseEnum.ERRO_INESPERADO);
            return responseDto;
        }

    }

    public BoletoResponseDto cancelarBoleto(Long id) {
        Optional<BoletoModelo> boleto = boletoRepository.findById(id);
        BoletoResponseDto responseDto = new BoletoResponseDto();
        if (boleto.isEmpty()) {
            responseDto.responseDto(BoletoResponseEnum.NAO_LOCALIZADO);
            return responseDto;
        }
        if (boleto.get().getStatus().equals(BoletoStatusEnum.PAGO)) {
            responseDto.responseDto(BoletoResponseEnum.PROIBIDO_CANCELAR);
            return responseDto;
        }
        try {
            boleto.get().setStatus(BoletoStatusEnum.CANCELADO);
            boletoRepository.save(boleto.get());
            responseDto.responseDto(BoletoResponseEnum.BOLETO_CANCELADO);
            return responseDto;
        } catch (Exception e) {
            responseDto.responseDto(BoletoResponseEnum.ERRO_INESPERADO);
            return responseDto;
        }
    }

}
