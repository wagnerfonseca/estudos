package com.curso.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.ItemVenda;

/**
 * Aula 23.3 - Itens de venda
 * #CadastroMestreDetalhe
 * 
 * ## Resposabilidades:
 * -- Calcular Valor Total
 * -- Adicionar Item na Tabela
 * -- Quantidade de Items na tabela
 * */

/* @SessionScope adiconado na versão 4.3 Spring MVC
 * para separar um objeto por scopo de sessao
 * SEM essa anotação, é criado um unico objeto de dividido entre as várias sessões
 * Essa anotação é interessante para trabalhar com objetos separados por usuários logados 
 * */
@SessionScope
@Component
public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal) // dentro do obejto o atributo que eu quero trabalhar
				.reduce(BigDecimal::add)  // reduz o valor somando (supondo que existe tres valores 5 + 9 + 1 = reduz para 15)
				.orElse(BigDecimal.ZERO); // se não houver nenhum item retorna o valor zerado  
	}

	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setCerveja(cerveja);
		itemVenda.setQuantidade(quantidade);
		itemVenda.setValorUnitario(cerveja.getValor());
		
		itens.add(itemVenda);
	}
	
	/** Total de itens na tabela */
	public int total() {
		return itens.size();
	}
}
