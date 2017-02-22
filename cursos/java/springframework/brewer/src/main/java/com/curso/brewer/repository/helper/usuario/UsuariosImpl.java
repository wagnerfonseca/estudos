package com.curso.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Grupo;
import com.curso.brewer.model.Usuario;
import com.curso.brewer.model.UsuarioGrupo;
import com.curso.brewer.repository.filter.UsuarioFilter;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional(readOnly = true) 
	public Optional<Usuario> porEmailEAtivo(String email) {		
		return 
			manager
				// esse select vai buscar o usuario ativo
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email)
				.getResultList()
				.stream()
				.findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery(
						"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)  // o Hibernate é capaz de capturar e montar esta consulta com o ID do obejto
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> filtrar(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // distinct na entidade principal para nao deixar repetir o usuario
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}

	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			/*
			 select * from usuario u 
			  inner join usuario_grupo ug on u.codigo = ug.codigo_usuario
			  inner join grupo g on ug.codigo_grupo = g.codigo			  
			  where (
			   u.codigo in (select codigo_usuario from usuario_grupo where codigo_grupo = 1) and
			   u.codigo in (select codigo_usuario from usuario_grupo where codigo_grupo = 2)
			   )
			 * */
			
			// Vou colocando em minha consulta os grupos que devem fazer parte da consulta			
			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN); // JoinType.LEFT_OUTER_JOIN para evitar de acontever o erro de LazyException.
			
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {				
				List<Criterion> subqueries = new ArrayList<>(); // Minhas subqueries
				//interando sobre os codigos selecionados
				for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {					
					//criando outra criteria para ser adicionada para a primeira criteria 
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					// Condições
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo)); // navegando no codigo a partir da classe UsuarioGrupo
					// O que eu quero que retorna do minha Criteria
					dc.setProjection(Projections.property("id.usuario")); // retorno dessa criteria
					
					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()]; // varargs é um array
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

}
