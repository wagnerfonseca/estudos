package com.curso.brewer.security;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Buscar o usuario atraves do email
		Optional<Usuario> opt =  usuarios.porEmailEAtivo(email);
		Usuario usuario = opt.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		
		
		// new HashSet<>() <- ainda não esta enviando nehuma permissão do usuário	
		//return new User(usuario.getEmail(), usuario.getSenha(), getPermissoes(usuario));
		
		// Vou usar herança para mostrar o nome do usuario em nosso BD para visualização
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		// Lista de permissões do usuario
		List<String> permissoes = usuarios.permissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));		
		
		return authorities;
	}

}
