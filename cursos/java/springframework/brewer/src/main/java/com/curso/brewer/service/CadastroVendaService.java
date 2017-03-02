package com.curso.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void salvar(Venda venda) {
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
				
		repository.save(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
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
