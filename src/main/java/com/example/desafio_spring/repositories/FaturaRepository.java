package com.example.desafio_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desafio_spring.models.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

}