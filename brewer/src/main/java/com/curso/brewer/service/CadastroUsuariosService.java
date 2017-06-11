package com.curso.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.curso.brewer.model.Usuario;
import com.curso.brewer.repository.Usuarios;
import com.curso.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.curso.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuariosService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, SenhaObrigatoriaUsuarioException {
		Optional<Usuario> opt = usuarios.findByEmail(usuario.getEmail());
		if (opt.isPresent() && !opt.get().equals(usuario))
			throw new EmailUsuarioJaCadastradoException("Ja existe um usuário cadastrado com este email");
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha()))
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(opt.get().getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(opt.get().getAtivo());
		}
		
		usuarios.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, usuarios);
	}
	
}
