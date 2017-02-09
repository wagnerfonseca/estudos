package com.curso.brewer.repository.filter;

import org.springframework.util.StringUtils;

import com.curso.brewer.model.Estado;

public class CidadeFilter {
	
	private String nome;
	private Estado estado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/* Não pode ter  a palavra "is" e "get" devido as nomeclaturas padrões 
	   de definição dos nomes na linguagem Java 
	   por isso tem que utilizar outro nome "tem","possui" 
	*/
	public boolean temNome() {
		return !StringUtils.isEmpty(nome);
	}
	
	public boolean temEstado() {
		return this.estado != null && this.estado.getCodigo() > 0;
	}

}
