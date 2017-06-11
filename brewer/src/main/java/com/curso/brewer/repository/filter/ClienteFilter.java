package com.curso.brewer.repository.filter;

import com.curso.brewer.model.TipoPessoa;

public class ClienteFilter {

	private String nome;
	private String cpfOuCnpj;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	public String getCpfOuCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(cpfOuCnpj);
	}
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}	
	
}
