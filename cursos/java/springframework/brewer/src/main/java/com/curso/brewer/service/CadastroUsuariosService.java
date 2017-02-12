package com.curso.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.Usuarios;
import com.curso.brewer.service.exception.EmailUsuarioJaCadastradoException;

@Service
public class CadastroUsuariosService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException {
		Optional<Usuario> opt = usuarios.findByEmail(usuario.getEmail());
		if (opt.isPresent())
			throw new EmailUsuarioJaCadastradoException("Ja existe um usuário cadastrado com este email");
		usuarios.save(usuario);
	}
	
}
