package com.curso.brewer.dto;

/*
 * Uma classe que não vai persistir dados no banco de dados
 * Ela apenas vai ser utilizadas para transferir dados por isso o prefixo DTO: Data Transfer Object
 * */
public class FotoDTO {

	private String nome;
	private String contentType;
	
	public FotoDTO(String nome, String contentType) {
		super();
		this.nome = nome;
		this.contentType = contentType;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
