package com.curso.brewer.model;

public enum StatusVenda {
	
	ORCAMENTO("Orcamento"),
	EMITIDA("Emitida"),
	CANCELADA("Cancelada");
	
	private String descricao;
	
	private StatusVenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static StatusVenda fromValue(String value) {
		return valueOf(value);
	}

}
