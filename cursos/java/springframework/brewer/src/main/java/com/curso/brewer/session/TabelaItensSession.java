package com.curso.brewer.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.ItemVenda;

/**
 * Essa classe é a minha sessão por scope (Usuario)
 * O Spring MVC não possui uma solução para Scopo por visão (Scope View)
 * 
 * Para Evitar ter que criar um Map de tabelas de itens 
 * Eu crio uma classe que vai possuir um conjunto de tabela e vou controlar atraves de um identificador(uuid) por view
 * 
 * */


/* @SessionScope adiconado na versão 4.3 Spring MVC
 * para separar um objeto por scopo de sessao
 * SEM essa anotação, é criado um unico objeto de dividido entre as várias sessões
 * Essa anotação é interessante para trabalhar com objetos separados por usuários logados 
 * */
@SessionScope
@Component
public class TabelaItensSession {
	
	// Conjunto de tabelas itens de vendas
	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabela = getTabelaItensVenda(uuid);
		tabela.adicionarItem(cerveja, 1);
		tabelas.add(tabela);
	}

	public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
		getTabelaItensVenda(uuid).alterarQuantidadeItens(cerveja, quantidade);
	}

	public void excluirItem(String uuid, Cerveja cerveja) {
		getTabelaItensVenda(uuid).excluirItem(cerveja);
	}

	public List<ItemVenda> getItens(String uuid) {
		return getTabelaItensVenda(uuid).getItens();
	}
	
	public BigDecimal getValorTotal(String uuid) {
		return getTabelaItensVenda(uuid).getValorTotal();
	}
	
	private TabelaItensVenda getTabelaItensVenda(String uuid) {
		return tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaItensVenda(uuid));
	}	
}
