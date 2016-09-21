package com.curso.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.service.event.cerveja.CervejaSalvaEvent;

/*
 * Parece desnecessário criar uma classe de serviço para chamar os métodos do repository.
 * Mas a intenção aqui é separar a camada de negócios 
 * */

@Service
public class CadastroCervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	// Com essa anotação agora eu estou responsavel por controlar as transações
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
		
		/*
		 * publicar um evento para execução de listener(ouvintes)
		 * Essa solução para desacoplar a classe nas funções que são necessárias depois de salvar um objeto,
		 * por ex. Enviar Email, redimensionar foto, etc
		 * */
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
	
		
	

}
