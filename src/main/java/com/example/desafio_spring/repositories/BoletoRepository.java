package com.example.desafio_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.desafio_spring.models.BoletoModelo;

public interface BoletoRepository extends JpaRepository<BoletoModelo, Long> {
    
}
