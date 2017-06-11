package com.curso.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.brewer.dto.CervejaDTO;
import com.curso.brewer.dto.ValorItensEstoque;
import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {
	
	Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	/** 
	 * Consulta de todas as cervejas
	 * Como não vai ser necessário todos os dados da cerveja vamos criar uma classe apenas para armazenar esses dados CervejaDTO */	
	List<CervejaDTO> porSkuOuNome(String skuOuNome);
	
	ValorItensEstoque valorItensEstoque();
	

}
