package com.example.desafio_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.desafio_spring.models.FaturaRegistrada;

@Repository
public interface FaturaRegistradaRepository extends JpaRepository<FaturaRegistrada, Long> {

	@Query("from FaturaRegistrada where fatura.id = :faturaId")
	FaturaRegistrada buscarPorFaturaId(Long faturaId);

}
