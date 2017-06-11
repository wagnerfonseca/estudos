package com.curso.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


/**
 Essa classe foi criada para um pedaço de codigo que estava redundante, ou seja, repedito em mais de uma classe
 
 @Component é uma anotação para o Spring poder injetar
 * */
@Component
public class PaginacaoUtil {
	
	public void preparar(Criteria criteria, Pageable pageable) {
		// Pageable um objeto que possui informações referente a pagina
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		// Realizar Ordenação dos dados
		// Essa informação vem pelo objeto Pageable
		Sort sort = pageable.getSort();
		if (sort != null) {
			// Sort.Order para não confundir com o outro pacote que tem o mesmo nome
			Sort.Order order = sort.iterator().next(); // quais são os campos que vão ser ordenados, e como serão ordenados
			String field = order.getProperty(); // o campo que vai ser odenado
			
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field) ); 
		}
	}

}
