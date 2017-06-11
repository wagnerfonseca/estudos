package com.curso.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.curso.brewer.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	/* Eu preciso de criar uma sequencia de validaçoes 
	 * 
	 *  As sequencias de validações são executadas na ordem, enquando a primeira não é verificada,
	 *  não valida a segunda
	 *  */
	
	@Override
	public List<Class<?>> getValidationGroups(Cliente source) {
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Cliente.class); // Valida toda as anotações dessa classe que não possui nenhuma anotação sobre grupo
		
		// So vou validar quando houver informações a respeito tipo de pessoa
		if (isPessoaSelecionada(source))
			grupos.add(source.getTipoPessoa().getGrupo()); // adiciono o grupo conforme o tipo de pessoa
		
		return grupos;
	}

	private boolean isPessoaSelecionada(Cliente source) {
		return source != null && source.getTipoPessoa() !=  null;
	}
	
}
