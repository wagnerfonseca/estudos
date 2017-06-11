package com.curso.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.model.ItemVenda;
import com.curso.brewer.repository.Cervejas;

@Component
public class VendaListener {
	
	@Autowired
	private Cervejas cervejas;
	
	@EventListener // anotação que fica responsavel pela execução deste metodo
	public void vendaEmitida(VendaEvent event) {
		for(ItemVenda item: event.getVenda().getItens()) {
			Cerveja cerveja = cervejas.findOne(item.getCerveja().getCodigo()); 
			cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
			cervejas.save(cerveja);
		}
	}

}
