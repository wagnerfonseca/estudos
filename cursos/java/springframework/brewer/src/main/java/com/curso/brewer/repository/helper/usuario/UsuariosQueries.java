package com.curso.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public List<Usuario> filtrar(UsuarioFilter filtro);
}
