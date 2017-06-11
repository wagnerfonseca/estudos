package com.curso.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.curso.brewer.storage.FotoStorage;

@Component
public class CervejaListener {
	
	@Autowired
	private FotoStorage fotoStorage;
	/*
	 * Em vez de criar um if para verificar se existe foto você pode colocar 
	 * uma condicao para executar este listener
	 * Seguindo o padrão javaBean no nome do método "isFoto", a condição pode ser assim : #event.foto
	 * Mas caso o nome do método não segue o padrão JavaBean, a condição deve chamar o nome exato do evento: #event.temFoto()
	 * */
	@EventListener(condition = "#event.temFoto() and #event.novaFoto")
	public void cervejaSalva(CervejaSalvaEvent event) {
		
		fotoStorage.salvar(event.getCerveja().getFoto());
	}

}
