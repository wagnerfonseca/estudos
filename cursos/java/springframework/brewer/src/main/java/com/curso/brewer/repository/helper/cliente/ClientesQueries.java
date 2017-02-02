package com.curso.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.model.Cliente;
import com.curso.brewer.repository.filter.ClienteFilter;

public interface ClientesQueries {

	Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
	
}
