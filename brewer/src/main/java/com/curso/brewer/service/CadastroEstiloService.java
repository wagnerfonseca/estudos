package com.curso.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Estilo;
import com.curso.brewer.repository.Estilos;
import com.curso.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private Estilos service;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		// Lançar uma exceção para evitar dados repetidos
		Optional<Estilo> estiloOpt = service.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOpt.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome já cadastrado");
		}
		return service.saveAndFlush(estilo);
	}

}
