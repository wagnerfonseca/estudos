package com.curso.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.brewer.model.Cliente;
import com.curso.brewer.repository.helper.cliente.ClientesQueries;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}
