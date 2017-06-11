package com.curso.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.Cervejas;
import com.curso.brewer.service.event.cerveja.CervejaSalvaEvent;
import com.curso.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.curso.brewer.storage.FotoStorage;

/*
 * Parece desnecessário criar uma classe de serviço para chamar os métodos do repository.
 * Mas a intenção aqui é separar a camada de negócios 
 * */

@Service
public class CadastroCervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private FotoStorage fotoStorage;
	
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
	
	
	@Transactional
	public void excluir(Cerveja cerveja) {
		try {
			String foto = cerveja.getFoto();
			cervejas.delete(cerveja);
			cervejas.flush(); // executar delete imediatamente no banco de dados
			fotoStorage.excluir(foto);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
		}
	}
	
		
	

}
