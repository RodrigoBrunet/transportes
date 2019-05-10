package com.e2r.transportes.repository.helper.cliente;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2r.transportes.model.Cliente;
import com.e2r.transportes.repository.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
	
}
