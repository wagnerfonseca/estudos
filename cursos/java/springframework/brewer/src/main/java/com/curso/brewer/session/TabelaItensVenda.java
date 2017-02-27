package com.curso.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
 * -- Alterar quantidade de cerveja em um item
 * -- Excluir um item de venda
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
		Optional<ItemVenda> itemVendaOpt = buscarItemPorCerveja(cerveja);
			
		ItemVenda itemVenda = null;
		if (itemVendaOpt.isPresent()) {
			itemVenda = itemVendaOpt.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		} else {		
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());
			itens.add(0, itemVenda);
		}
	}
	
	/** Alterar a quantidade de produto em um item */
	public void alterarQuantidadeItens(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = buscarItemPorCerveja(cerveja).get();
		itemVenda.setQuantidade(quantidade);
	}
	
	
	/** Excluir um item  */
	public void excluirItem(Cerveja cerveja) {
		// o remove de ArrayList é por indice
		// E eu tenho apenas o objeto de cerveja, vou procurar pelo indice na lista de itens atraves do objeto de cerveja
		int indice = IntStream.range(0, itens.size())
						.filter(i -> itens.get(i).getCerveja().equals(cerveja))
						.findAny()
						.getAsInt();
		itens.remove(indice);
	}
	
	/** Total de itens na tabela */
	public int total() {
		return itens.size();
	}


	public List<ItemVenda> getItens() {		
		return itens;
	}
	
	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		// Adicionando o mesma cerveja para lista de itens de venda
		Optional<ItemVenda> itemVendaOpt = itens.stream()
				// filtra apenas os itens que são iguais ao objeto comparado
				.filter(i -> i.getCerveja().equals(cerveja)) 
				.findAny(); // encontro qualquer um
		return itemVendaOpt;
	}
	
}
