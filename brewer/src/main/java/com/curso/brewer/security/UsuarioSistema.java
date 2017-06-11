package com.curso.brewer.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.curso.brewer.model.Usuario;

public class UsuarioSistema extends User {
	
	/*
	 * Essa classe foi criada no intuido de ter um objeto que herde de User e possua os dados da classe Usuario
	 * 
	 * */
	
	private static final long serialVersionUID = 1L;
	
	// Obejto com os nossos dados de usuario
	private Usuario usuario;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);		
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}	

}
