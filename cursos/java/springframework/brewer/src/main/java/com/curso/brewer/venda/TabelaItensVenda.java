package com.curso.brewer.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.curso.brewer.model.ItemVenda;

/**
 * Aula 23.3 - Itens de venda
 * 
 * ## Resposabilidades:
 * -- Calcular Valor Total
 * */

public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal) // dentro do obejto o atributo que eu quero trabalhar
				.reduce(BigDecimal::add)  // reduz o valor somando (supondo que existe tres valores 5 + 9 + 1 = reduz para 15)
				.orElse(BigDecimal.ZERO); // se não houver nenhum item retorna o valor zerado  
	}

}
