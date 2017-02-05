package com.curso.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Cidade;
import com.curso.brewer.repository.Cidades;
import com.curso.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CadastroCidadesService {
	
	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) throws NomeCidadeJaCadastradaException {
		Optional<Cidade> opt = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if (opt.isPresent())
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrada");
		cidades.saveAndFlush(cidade);
	}

}
