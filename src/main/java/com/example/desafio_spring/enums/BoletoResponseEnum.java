package com.example.desafio_spring.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum BoletoResponseEnum {
    NAO_LOCALIZADO("O boleto não foi localizado.", HttpStatus.NOT_FOUND),
    LOCALIZADO("O boleto localizado.", HttpStatus.OK), 
    BOLETO_PAGO("O boleto foi pago.", HttpStatus.OK),
    PROIBIDO_PAGAR("Pagamento não concluído! Boleto está cancelado.", HttpStatus.BAD_REQUEST),
    BOLETO_CANCELADO("O boleto foi cancelado.", HttpStatus.OK),
    PROIBIDO_CANCELAR("Não é possível cancelar! O boleto já foi pago.", HttpStatus.BAD_REQUEST),
    ERRO_INESPERADO("Ocorreu um erro inesperado.", HttpStatus.INTERNAL_SERVER_ERROR);

    private String descricao;
    private HttpStatus httpStatus;

    BoletoResponseEnum(String descricao, HttpStatus httpStatus) {
        this.descricao = descricao;
        this.httpStatus = httpStatus;
    }

}
