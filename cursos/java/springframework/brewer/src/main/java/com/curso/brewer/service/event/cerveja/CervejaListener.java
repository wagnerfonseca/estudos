package com.curso.brewer.service.event.cerveja;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CervejaListener {
	
	
	/*
	 * Em vez de criar um if para verificar se existe foto você pode colocar uma condicao para executar este listener
	 * Seguindo o padrão javaBean no nom do método "isFoto", a condição pode ser assim : #event.foto
	 * Mas caso o nome do método não segue o padrão JavaBean, a condição deve chamar o nome exato do evento: #event.temFoto()
	 * */
	@EventListener(condition = "#event.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent event) {
		System.out.println("Cerveja salva: " + event.getCerveja().getNome());
	}

}
