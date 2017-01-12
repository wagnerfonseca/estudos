package com.curso.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Estilo;

import com.curso.brewer.repository.filter.EstiloFilter;

public class EstilosImpl implements EstilosQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")	
	@Override
	@Transactional(readOnly = true) 
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {	
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field) ); 
		}
		
		adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, totalPaginas(filter));
	}
	
	private Long totalPaginas(EstiloFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	
	private void adicionarFiltro(EstiloFilter filter, Criteria criteria) {
		if (filter != null) {
			if (filter.isNomePresent())
				criteria.add(Restrictions.eq("nome", filter.getNome()));
		}
	}
		
}
