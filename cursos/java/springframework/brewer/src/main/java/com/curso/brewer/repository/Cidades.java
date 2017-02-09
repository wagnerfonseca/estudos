package com.curso.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.brewer.model.Cidade;
import com.curso.brewer.model.Estado;
import com.curso.brewer.repository.helper.cidade.CidadesQueries;

public interface Cidades  extends JpaRepository<Cidade, Long>, CidadesQueries {
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);

}
