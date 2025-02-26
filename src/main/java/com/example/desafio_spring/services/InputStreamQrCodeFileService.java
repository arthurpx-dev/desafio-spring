package com.example.desafio_spring.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;


@Service
public class InputStreamQrCodeFileService implements GeradorQrCodeService {


	@Override
    public InputStream gerar(String codigo) {
        try {
            int width = 250;
            int height = 250;

            BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(codigo, BarcodeFormat.QR_CODE, width, height);

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }
    
}
