package com.curso.brewer.model;

public enum Origem {
	
	INTERNACIONAL("Internacional"),
	NACIONAL("Nacional");
	
	private String descricao;
	
	private Origem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}	
	
	public Origem fromValue(String descricao) {
		return valueOf(descricao);
	}

}
