package com.curso.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.brewer.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long> {

}
