package com.e2r.transportes.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2r.transportes.model.Estilo;
import com.e2r.transportes.repository.filter.EstiloFilter;

public interface EstilosQueries {
	
	public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);

}
