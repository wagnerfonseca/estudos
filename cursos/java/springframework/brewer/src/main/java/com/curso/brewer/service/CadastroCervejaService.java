package com.curso.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.Cervejas;

/*
 * Parece desnecessário criar uma classe de serviço para chamar os métodos do repository.
 * Mas a intenção aqui é separar a camada de negócios 
 * */

@Service
public class CadastroCervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	}

}
