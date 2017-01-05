package com.curso.brewer.repository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {
	
	Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);

}
