package com.example.desafio_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desafio_spring.models.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}