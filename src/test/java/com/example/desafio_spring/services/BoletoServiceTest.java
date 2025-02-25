package com.example.desafio_spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafio_spring.enums.BoletoStatusEnum;

import com.example.desafio_spring.models.BoletoModelo;
import com.example.desafio_spring.repositories.BoletoRepository;

@ExtendWith(MockitoExtension.class)
public class BoletoServiceTest {

    @Mock
    private BoletoRepository boletoRepository;

    @InjectMocks
    private BoletoService boletoService;

    @Test
    void testBuscarBoletoPorId() {
       Long id = 1L;
       BoletoModelo boleto = new BoletoModelo();
        boleto.setId(id);
        boleto.setCodigo("123456789");

        // Simula o comportamento do repository
        when(boletoRepository.findById(id)).thenReturn(Optional.of(boleto));

        // Executa o método do service
        Optional<BoletoModelo> result = boletoService.buscarBoletoPorId(id);

        // Verificações
        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().getCodigo());
        verify(boletoRepository).findById(id);
    }

    @Test
    void testBuscarBoletos() {

    }

    @Test
    void testCancelarBoleto() {
        Long id = 1L;
        BoletoModelo boleto = new BoletoModelo();
        boleto.setId(id);
        boleto.setStatus(BoletoStatusEnum.ABERTO);

        when(boletoRepository.findById(id)).thenReturn(Optional.of(boleto));
        when(boletoRepository.save(any(BoletoModelo.class))).thenReturn(boleto);

        boletoService.cancelarBoleto(id);

        assertEquals(BoletoStatusEnum.CANCELADO, boleto.getStatus());
        verify(boletoRepository).findById(id);
        verify(boletoRepository).save(boleto);

    }

    @Test
    void testCriarBoleto() {
        BoletoModelo boleto = new BoletoModelo();
        boleto.setCodigo("123456789");
        boleto.setValor(100.0);
        boleto.setDataVencimento(LocalDate.of(2025, 2, 28));
        boleto.setStatus(BoletoStatusEnum.ABERTO);

        // Simula o save
        when(boletoRepository.save(boleto)).thenReturn(boleto);

        // Executa o método do service
        BoletoModelo result = boletoService.criarBoleto(boleto);

        // Verificações
        assertNotNull(result);
        assertEquals("123456789", result.getCodigo());
        verify(boletoRepository).save(boleto);
    }

    @Test
    void testPagarBoleto() {
        Long id = 1L;
        BoletoModelo boleto = new BoletoModelo();
        boleto.setId(id);
        boleto.setStatus(BoletoStatusEnum.ABERTO);

        when(boletoRepository.findById(id)).thenReturn(Optional.of(boleto));
        when(boletoRepository.save(any(BoletoModelo.class))).thenReturn(boleto);

        boletoService.pagarBoleto(id);

        assertEquals(BoletoStatusEnum.PAGO, boleto.getStatus());
        assertNotNull(boleto.getDataPagamento());
        verify(boletoRepository).findById(id);
        verify(boletoRepository).save(boleto);

    }
}
