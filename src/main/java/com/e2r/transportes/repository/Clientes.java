package com.e2r.transportes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e2r.transportes.model.Cliente;
import com.e2r.transportes.repository.helper.cliente.ClientesQueries;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}
