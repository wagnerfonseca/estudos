package com.curso.brewer.repository.helper.cerveja;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Cerveja;
import com.curso.brewer.repository.filter.CervejaFilter;

/*
 Esta classe vai implementar um método para uma consulta customizada.
 O Spring oferece um facilitador para apenas declarar o nome do método e em tempo de execução
 ele cria as consultas no banco de dados.
 Neste caso vamos escrever essa consulta usando o CreateCriteria, para uma query customizada.
 para não haver necessidade de criar um novo bean para executar essas consultas, você implementa a 
 interface que o seu Repositoruy esta extendo. Assim você continua a utilizar o seu Bean, com novas
 funcionalidades.
 
 O nome deve obedecer a um padrão especifico do Spring, que é o nome da interface 
 Repository mais o nome posfixo "Impl". mas você pode altarer na classe de configuração. 
 Atribuindo o nome posfixo para a propriedade "repositoryImplementationPostfix" da anotação
 "EnableJpaRepositories".  
 * */

public class CervejasImpl implements CervejasQueries {
	
	/*
	 * O EntityManager é um contexto de persistência. Neste contexto estão todas as entidades
	 * Ele é o gerenciador das intâncias de entidades de persistências.
	 * 
	 * Para injetar um objeto do tipo EntityManager, você não utiliza o @Autowired e sim @PersistenceContext
	 * */
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	// Como nos retiramos as transações automaticas, deve ser implementar com esta aontação
	// Por ser uma transação somente para pesquisa, ela pode ser somente leitura
	@Transactional(readOnly = true) 
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		// manager.unwrap para retirar a Sessão do Hibernate para criar o objeto de Criteria 
		// esta consulta vai ser para a Entidade Cerveja
		// Outra vantagem da utilização da Criteria é a join de tabelas
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
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
		
		adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	/**
	 * Responsavel por calcular o total de registros
	 * */
	private Long total(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CervejaFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}

			if (isEstiloPresente(filter)) {
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			}

			if (filter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			}

			if (filter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			}

			if (filter.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			}

			if (filter.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filter.getValorAte()));
			}
		}
	}
	
	private boolean isEstiloPresente(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}

}
