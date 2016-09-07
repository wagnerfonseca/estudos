package com.curso.brewer.model;

public enum Sabor {
	
	AMARGA("Amarga"),
	ADOCICADA("Adocicada"),
	FORTE("Forte"),
	FRUTADA("Frutada"),
	SUAVE("Suave");
	
	private String descricao;
	
	private Sabor(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Sabor fromValue(String descricao) {
		return valueOf(descricao);
	}
}
