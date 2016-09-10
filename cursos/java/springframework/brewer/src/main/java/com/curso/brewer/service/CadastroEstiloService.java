package com.curso.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Estilo;
import com.curso.brewer.repository.Estilos;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private Estilos service;
	
	@Transactional
	public void salvar(Estilo estilo) {
		service.save(estilo);
	}

}
