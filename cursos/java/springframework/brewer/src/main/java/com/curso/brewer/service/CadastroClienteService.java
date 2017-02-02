package com.curso.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.Cliente;
import com.curso.brewer.repository.Clientes;
import com.curso.brewer.service.exception.CpfCnpjClienteCadastradoException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private Clientes clientes;
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> opt = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		if (opt.isPresent())
			throw new CpfCnpjClienteCadastradoException("CPF/CNPJ já cadastrado");
		clientes.save(cliente);
	}

}
