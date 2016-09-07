package com.curso.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.brewer.model.Cerveja;

// Representa a minha coleção de cervejas

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long> {
	
	public Optional<Cerveja> findBySkuIgnoreCase(String sku);

}
