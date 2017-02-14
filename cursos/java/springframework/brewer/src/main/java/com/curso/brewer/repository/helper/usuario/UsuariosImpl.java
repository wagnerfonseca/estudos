package com.curso.brewer.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Usuario;

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

}
