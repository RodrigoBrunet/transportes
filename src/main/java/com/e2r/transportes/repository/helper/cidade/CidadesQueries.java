package com.e2r.transportes.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2r.transportes.model.Cidade;
import com.e2r.transportes.repository.filter.CidadeFilter;

public interface CidadesQueries {
	
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pegeable);
	
	

}
