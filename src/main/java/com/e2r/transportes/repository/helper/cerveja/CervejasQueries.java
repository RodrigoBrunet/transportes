package com.e2r.transportes.repository.helper.cerveja;

import com.e2r.transportes.repository.filter.CervejaFilter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2r.transportes.dto.CervejaDTO;
import com.e2r.transportes.model.Cerveja;

public interface CervejasQueries  {
	
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);

}
