package com.curso.brewer.repository.helper.usuario;

import java.util.Optional;

import com.curso.brewer.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
}
