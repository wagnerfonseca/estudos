package com.curso.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/** 
 * Essa classe vai ser responsavel por retornar dados referente a pagina
 * Foi colocado dentro do pacote controller por ser relacionado mas próximo as View (Página HTML)
 * 
 * Uma Classe envelopadora, ela captura o Page e manipulo o seu retorno
 * A vantagem desse tipo de abordagem é evitar a repetição de codigo pra diferentes entidades de classes 
 * que vão utilizar dos mesmos métodos
 * Todos os comandos que estão na pagina (xhtml) e estão sendo usados pelo thymeleaf, 
 * serão empacotados por essa classe
 * */
public class PageWrapper<T> {
	
	private Page<T> page;
	/**
	 * Para não perder o filtro de pesquisa junto com a informação da página requisitada
	 * preciso de um construtor de URI's
	 * */
	private UriComponentsBuilder uriBuilder;

	/**
	 * Quando for criar um objeto de PageWrapper tem que passar o objeto page */
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		// Para construir esse meu URI, eu vou cosntruir esse URI através do HttpServletRequest, 
		// que possui as informações de requisições do client
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
	}
	
	/**
	 * Retornar o conteudo da página, como a lista de objetos de T
	 * */
	public List<T> getConteudo() {
		return page.getContent();
	}
	
	/**
	 * retorna se o conteudo da página esta vazio
	 * */
	public boolean isVazia() {
		return page.getContent().isEmpty();
	}
	
	/**
	 * Retorna qual é a pagina atual
	 * */
	public  int getAtual() {
		return page.getNumber();
	}
	
	/**
	 * Primeira página
	 * */
	public boolean isPrimeira() {
		return page.isFirst();
	}
	
	public boolean isUltima() {
		return page.isLast();
	}
	
	public int getTotalPaginas(){
		return page.getTotalPages();
	}
	
	/**
	 * Retornar a URL através da pagina informada
	 * 
	 * UriBuilder possui todo o endereço de url, e vou concatenar com a pagina informada
	 * */
	public String urlParaPagina(int pagina) {
		// Se exister alguma string "page" vai ser substituida, se não sera criado 
		return uriBuilder.replaceQueryParam("page", pagina)
				.build(true) // decodificar
				.encode() // encodificar
				.toUriString();
	}

}
