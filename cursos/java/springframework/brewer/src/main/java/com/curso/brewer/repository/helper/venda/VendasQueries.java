package com.curso.brewer.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.model.Venda;
import com.curso.brewer.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public Venda buscaVendaPorCodigoComItens(Long codigo);
	
}
