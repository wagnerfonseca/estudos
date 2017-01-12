package com.curso.brewer.repository.filter;

import org.springframework.util.StringUtils;

public class EstiloFilter {
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isNomePresent() {
		return !StringUtils.isEmpty(nome);
	}

}
