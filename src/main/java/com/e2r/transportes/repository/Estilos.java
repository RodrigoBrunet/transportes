package com.e2r.transportes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e2r.transportes.model.Estilo;
import com.e2r.transportes.repository.helper.estilo.EstilosQueries;

public interface Estilos extends JpaRepository<Estilo, Long>, EstilosQueries {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

	

}
