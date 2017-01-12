package com.curso.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.model.Estilo;
import com.curso.brewer.repository.filter.EstiloFilter;

public interface EstilosQueries {
	
	Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable);

}
