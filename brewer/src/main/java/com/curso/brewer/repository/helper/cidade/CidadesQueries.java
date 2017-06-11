package com.curso.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.model.Cidade;
import com.curso.brewer.repository.filter.CidadeFilter;

public interface CidadesQueries {
	
	Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);

}
