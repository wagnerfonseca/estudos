package com.curso.brewer.repository.helper.cerveja;

import java.util.List;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {
	
	List<Cerveja> filtrar(CervejaFilter filter);

}
