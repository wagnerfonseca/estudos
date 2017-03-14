package com.curso.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.brewer.model.StatusVenda;
import com.curso.brewer.model.Venda;
import com.curso.brewer.repository.Vendas;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas repository;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if (venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida/cancelada");
		}
		
		if (venda.isNova())
			venda.setDataCriacao(LocalDateTime.now());		
		
		
		//Calcular o valor total 
		/*BigDecimal valorTotalItens = 
				venda.getItens().stream()
					.map(ItemVenda::getValorTotal)
					.reduce(BigDecimal::add)
					.get();
		
		venda.setValorTotal(calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto()));*/
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega()
					, venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
				
		// saveAndFlush -> salva e retorna as alterações realizadas no banco de dados com o valor do id
		return repository.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}

	/* 
	 * #AcessoNegadoEmMetodos #SpringSecurity
	 * @PreAuthorize Anotação para especificar o controle de acesso ao metodo 
	 * A expressão será validada para decidir se a invocação do metodo é permitido ou não.
	 * #venda é para identificar o obejto que esta sendo recebido pelo parametro do metodo
	 * principal.usuario -> usuario logado
	 * */
	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		
		Venda vendaExistente = repository.findOne(venda.getCodigo());
		
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		repository.save(vendaExistente);
		
	}

	/*
	 * Em um programa de itens de pedido é necessário calcular o valor total antes de gravar 
	 * 
	 * Resposabilidade transferida para classe de venda
	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		BigDecimal valorTotal = 
				valorTotalItens
					.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
					.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
	}*/ 

}
