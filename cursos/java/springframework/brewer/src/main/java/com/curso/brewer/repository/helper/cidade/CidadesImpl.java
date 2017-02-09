package com.curso.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Cidade;
import com.curso.brewer.repository.filter.CidadeFilter;
import com.curso.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {

	/*
	 * O EntityManager é um contexto de persistência. Neste contexto estão todas as entidades
	 * Ele é o gerenciador das intâncias de entidades de persistências.
	 * 
	 * Para injetar um objeto do tipo EntityManager, você não utiliza o @Autowired e sim @PersistenceContext
	 * */
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil PaginacaoUtil; 

	@SuppressWarnings("unchecked")
	@Override
	// Como nos retiramos as transações automaticas, deve ser implementar com esta aontação
	// Por ser uma transação somente para pesquisa, ela pode ser somente leitura
	@Transactional(readOnly = true) 
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		PaginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		criteria.createAlias("estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));

	}

	private Long total(CidadeFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CidadeFilter filter, Criteria criteria) {
		if (filter != null) {
			/* Não pode ter  a palavra "is" e "get" devido as nomeclaturas padrões 
			   de definição dos nomes na linguagem Java 
			   por isso tem que utilizar outro nome "tem","possui"
			*/
			if (filter.temNome())
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			if (filter.temEstado())
				criteria.add(Restrictions.eq("estado", filter.getEstado()));
		}		
	}
}
